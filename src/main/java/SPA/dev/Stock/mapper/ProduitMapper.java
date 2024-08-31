package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.SousCategorieRepository;
import SPA.dev.Stock.repository.UserRepository;
import SPA.dev.Stock.repository.VenteRepository;
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
        return produits.stream()
                .map(produit -> {
                    ProduitDto produitDto = toDto(produit);

                    List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByProduit(produit);
                    if (!approvisionnements.isEmpty()) {
                        Approvisionnement lastAppro = approvisionnements.get(approvisionnements.size() - 1);
                        produitDto.setPrixUnitaire(lastAppro.getPrixUniteVente());
                    } else {
                        produitDto.setPrixUnitaire(0);
                    }

                    Integer quantiteApprovisionnee = approvisionnementRepository.findTotalQuantityByProduitIdAndCreatedBy(
                            produit.getIdProduit(),
                            userService.getCurrentUserId()
                    );
                    quantiteApprovisionnee = (quantiteApprovisionnee != null) ? quantiteApprovisionnee : 0;

                    Integer quantiteVendue = venteRepository.findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
                            produit.getIdProduit(),
                            userService.getCurrentUserId()
                    );
                    quantiteVendue = (quantiteVendue != null) ? quantiteVendue : 0;

                    produitDto.setQuantite(quantiteApprovisionnee - quantiteVendue);
                    return produitDto;
                })
                .collect(Collectors.toList());
    }
}