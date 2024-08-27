package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.SousCategorie;
import SPA.dev.Stock.repository.CategorieRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import SPA.dev.Stock.repository.SousCategorieRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Component
@RequiredArgsConstructor
public class ProduitMapper {
    private final SousCategorieRepository sousCategorieRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
   public ProduitDto toDto(Produit produit){
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
   public Produit toEntity(ProduitDto produitDto){
       return Produit.builder()
               .idProduit(produitDto.getIdProduit())
               .image(produitDto.getImage())
               .seuil(produitDto.getSeuil())
               .description(produitDto.getDescription())
               .designation(produitDto.getDesignation())
               .sousCategorie(sousCategorieRepository
                       .findById(parseInt(produitDto.getSousCategorie()))
                       .orElseThrow(()->new RuntimeException("sous categorie introuvable")))
               .reference(produitDto.getReference())
               .build();
   }
 public    List<ProduitDto> toDtoList(List<Produit> produits){
     return produits.stream()
             .map(produit -> {
                 // Personnalisation des données : concaténation de designation et reference
                 ProduitDto produitDto = toDto(produit);
                     produitDto.setImage(STR."\{uploadDir}/\{produit.getImage()}");
                 return produitDto;
             })
             .collect(Collectors.toList());
 }
}
