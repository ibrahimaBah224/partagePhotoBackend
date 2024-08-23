package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.SousCategorie;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProduitMapper {
   public ProduitDto toDto(Produit produit){
       return ProduitDto.builder()
               .idProduit(produit.getIdProduit())
               .image(produit.getImage())
               .seuil(produit.getSeuil())
               .description(produit.getDescription())
               .designation(produit.getDesignation())
               .id_sousCategorie(produit.getSousCategorie().getIdSousCategorie())
               .reference(produit.getReference())
               .build();
   }
   public Produit toEntity(ProduitDto produitDto, SousCategorie sousCategorie){
       return Produit.builder()
               .idProduit(produitDto.getIdProduit())
               .image(produitDto.getImage())
               .seuil(produitDto.getSeuil())
               .description(produitDto.getDescription())
               .designation(produitDto.getDesignation())
               .sousCategorie(sousCategorie)
               .reference(produitDto.getReference())
               .build();
   }
 public    List<ProduitDto> toDtoList(List<Produit> produits){
       return produits.stream()
               .map(this::toDto)
               .collect(Collectors.toList());
 }
}
