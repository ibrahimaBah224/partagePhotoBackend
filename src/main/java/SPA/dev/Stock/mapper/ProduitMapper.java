package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.modele.Produit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDto toDto(Produit produit);
    Produit toEntity(ProduitDto produitDto);
    List<ProduitDto> toDtoList(List<Produit> produits);
}
