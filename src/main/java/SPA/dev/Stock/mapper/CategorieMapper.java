package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.CategorieDto;
import SPA.dev.Stock.modele.Categorie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategorieMapper  {
    CategorieDto toDto(Categorie categorie);
    Categorie toEntity(CategorieDto categorieDto);
    List<CategorieDto> toDtoList(List<Categorie> categories);
}
