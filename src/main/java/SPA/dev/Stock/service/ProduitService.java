package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.mapper.ProduitMapper;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.ProduitRepository;
import SPA.dev.Stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitMapper produitMapper;
    private final ProduitRepository produitRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Ajouter un produit avec gestion de l'image
    public ProduitDto ajouter(ProduitDto produitDto, MultipartFile file) throws IOException {
        produitDto.setCreatedBy(userService.getCurrentUserId());
        Produit produit = produitMapper.toEntity(produitDto);

        // Gestion de l'image
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            Path filePath = Paths.get(uploadDir + "/" + fileName);
            Files.copy(file.getInputStream(), filePath);
            produit.setImage(fileName);  // Stocker uniquement le nom du fichier dans la base de données
        }

        produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    // Récupérer la liste des produits en fonction du rôle de l'utilisateur
    public List<ProduitDto> liste() {
        int userId = userService.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        List<Produit> produits;
        if (user.getRole() == RoleEnumeration.SUPER_ADMIN) {
            produits = produitRepository.findAll();  // Super admin peut voir tous les produits
        } else {
            User admin = userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Super admin introuvable"));
            produits = produitRepository.findProduitsByCreatedBy(userId);
            produits.addAll(produitRepository.findProduitsByCreatedBy(admin.getId()));
        }

        return produitMapper.toDtoList(produits);
    }

    // Récupérer un produit par ID
    public Optional<ProduitDto> getProduit(int id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return Optional.of(produitMapper.toDto(produit));
    }

    // Supprimer un produit par ID
    public String delete(int id) {
        ProduitDto produitDto = getProduit(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        produitRepository.deleteById(id);
        return "Produit supprimé avec succès";
    }

    // Modifier un produit
    public ProduitDto modifier(int id, ProduitDto produitDto) {
        ProduitDto existingProduitDto = getProduit(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        Produit produit = produitMapper.toEntity(produitDto);
        produit.setIdProduit(id);
        produit.setCreatedBy(existingProduitDto.getCreatedBy());  // Conserver le créateur d'origine
        return produitMapper.toDto(produitRepository.save(produit));
    }
}
