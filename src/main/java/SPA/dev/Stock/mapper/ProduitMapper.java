package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.*;
import SPA.dev.Stock.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import static java.lang.Integer.parseInt;

@Component
@RequiredArgsConstructor
public class ProduitMapper {
    private final SousCategorieRepository sousCategorieRepository;
    private final ApprovisionnementRepository approvisionnementRepository;
    private final VenteRepository venteRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PerteRepository perteRepository;
    private final MagasinRepository magasinRepository;
    private int quantite = 0;

    public ProduitDto toDto(Produit produit) {
        return ProduitDto.builder()
                .idProduit(produit.getIdProduit())
                .image(produit.getImage())
                .seuil(produit.getSeuil())
                .description(produit.getDescription())
                .designation(produit.getDesignation())
                .sousCategorie(produit.getSousCategorie().getLibelle())
                .reference(produit.getReference())
                .build();
    }

    public Produit toEntity(ProduitDto produitDto) {
        return Produit.builder()
                .idProduit(produitDto.getIdProduit())
                .image(produitDto.getImage())
                .seuil(produitDto.getSeuil())
                .description(produitDto.getDescription())
                .designation(produitDto.getDesignation())
                .sousCategorie(sousCategorieRepository
                        .findById(parseInt(produitDto.getSousCategorie()))
                        .orElseThrow(() -> new RuntimeException("sous categorie introuvable")))
                .reference(produitDto.getReference())
                .build();
    }

    public List<ProduitDto> toDtoList(List<Produit> produits) {
        User user =  userRepository.findById(userService.getCurrentUserId())
                .orElseThrow(()->new RuntimeException("user not found"));
        return produits.stream()
                .map(produit -> {
                    ProduitDto produitDto = toDto(produit);

                    List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByProduitAndCreatedBy(produit,userService.getCurrentUserId());
                    if (!approvisionnements.isEmpty()) {
                        Approvisionnement lastAppro = approvisionnements.get(approvisionnements.size() - 1);
                        produitDto.setPrixUnitaire(lastAppro.getPrixUniteVente());
                    } else {
                        produitDto.setPrixUnitaire(0);
                    }
                    Integer quantiteApprovisionnee = 0;
                    if(user.getRole().equals(RoleEnumeration.SUPER_ADMIN) || user.getRole().equals(RoleEnumeration.ADMIN)){
                         quantiteApprovisionnee = approvisionnementRepository.findTotalQuantityByProduitIdAndCreatedBy(
                                produit.getIdProduit(),
                                userService.getCurrentUserId()
                        );
                    }else{
                        quantiteApprovisionnee = approvisionnementRepository.findTotalQuantityByProduitIdAndCreatedBy(
                                produit.getIdProduit(),
                                user.getCreatedBy()
                        );
                    }

                    quantiteApprovisionnee = (quantiteApprovisionnee != null) ? quantiteApprovisionnee : 0;
                    Integer quantiteVendue = 0;
                    if(user.getRole().equals(RoleEnumeration.SUPER_ADMIN) || user.getRole().equals(RoleEnumeration.ADMIN)) {

                         quantiteVendue = venteRepository.findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
                                produit.getIdProduit(),
                                userService.getCurrentUserId(),
                                userRepository.findById(userService.getCurrentUserId())
                                        .orElseThrow(() -> new RuntimeException("user not found"))
                        );
                    }else{
                         quantiteVendue = venteRepository.findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
                                produit.getIdProduit(),
                                userService.getCurrentUserId(),
                                userRepository.findById(user.getCreatedBy())
                                        .orElseThrow(() -> new RuntimeException("user not found"))
                        );
                    }
                    quantiteVendue = (quantiteVendue != null) ? quantiteVendue : 0;
                    Integer quantitePerdue = perteRepository.findTotalQuantitePerduByProduitAndCreatedByOrEntrepot(
                            produit.getIdProduit(),
                            userService.getCurrentUserId(),
                            user.getMagasin().getId()
                    );
                    quantitePerdue = (quantitePerdue != null) ? quantitePerdue : 0;
                    produitDto.setQuantite(quantiteApprovisionnee - (quantiteVendue+quantitePerdue));
                    return produitDto;
                })
                .collect(Collectors.toList());
    }
}