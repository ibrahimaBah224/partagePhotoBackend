package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.modele.SousCategorie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SousCategorieMapper  {
    SousCategorieDto toDto(SousCategorie sousCategorie);
    SousCategorie toEntity(SousCategorieDto sousCategorieDto);
    List<SousCategorieDto> toDtoList(List<SousCategorie> sousCategories);
}
