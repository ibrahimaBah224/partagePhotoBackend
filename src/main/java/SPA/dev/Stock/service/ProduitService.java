package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.mapper.ProduitMapper;
import SPA.dev.Stock.mapper.SousCategorieMapper;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.SousCategorie;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitMapper produitMapper;
    private final SousCategorieService sousCategorieService;
    private final SousCategorieMapper sousCategorieMapper;
    private final ProduitRepository produitRepository;
    private final UserService userService;
    private final  FileUploadService fileUploadService;
    public ProduitDto ajouter(ProduitDto produitDto, MultipartFile file) throws IOException {
        produitDto.setCreatedBy(userService.getCurrentUserId());

        SousCategorie sousCategorie = sousCategorieMapper.toEntity(
                sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie())
                        .orElseThrow(() -> new RuntimeException("Souscategorie introuvable"))
        );

        // Vérification si un fichier est présent
        if (file != null && !file.isEmpty()) {
            String photoFileName = fileUploadService.uploadFile(file);
            produitDto.setImage(photoFileName); // Enregistrer uniquement le nom du fichier dans produitDto
        } else {
            produitDto.setImage(null); // Optionnel: Vous pouvez décider de laisser l'image inchangée si aucune n'est fournie
        }

        Produit produit = produitMapper.toEntity(produitDto, sousCategorie);
        produit.setSousCategorie(sousCategorie);
        produitRepository.save(produit);

        ProduitDto p = produitMapper.toDto(produit);
        p.setId_sousCategorie(produitDto.getId_sousCategorie());
        return p;
    }

    public List<ProduitDto> liste() {
        return produitMapper.toDtoList(produitRepository.findAll());
    }

    public Optional<ProduitDto> getProduit(int id) {
        Produit produit = produitRepository.findById(id).orElseThrow(()->new RuntimeException("produit non trouver"));
        return Optional.of(produitMapper.toDto(produit));
    }

    public String delete(int id) {
        getProduit(id);
        produitRepository.deleteById(id);
        return "produit supprimer avec success";
    }

    public ProduitDto modifier(int id, ProduitDto produitDto) {
        getProduit(id);
        SousCategorie sousCategorie = sousCategorieMapper.toEntity(sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie()).orElseThrow(()->new RuntimeException("sousCategorie introuvable")));
        produitDto.setUpdatedBy(userService.getCurrentUserId());
        Produit produit = produitMapper.toEntity(produitDto,sousCategorie);
        sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie());
        produit.setIdProduit(id);
        return produitMapper.toDto(produitRepository.save(produit));
    }

}
