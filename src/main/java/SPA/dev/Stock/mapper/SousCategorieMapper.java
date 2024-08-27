package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.SousCategorie;
import SPA.dev.Stock.repository.CategorieRepository;
import SPA.dev.Stock.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SousCategorieMapper  {

    private final CategorieRepository categorieRepository;
   public SousCategorieDto toDto(SousCategorie sousCategorie){
       return SousCategorieDto.builder()
               .idSousCategorie(sousCategorie.getIdSousCategorie())
               .idCategorie(sousCategorie.getCategorie().getIdCategorie())
               .description(sousCategorie.getDescription())
               .libelle(sousCategorie.getLibelle())
               .build();
   }
   public SousCategorie toEntity(SousCategorieDto sousCategorieDto){
       return SousCategorie.builder()
               .idSousCategorie(sousCategorieDto.getIdSousCategorie())
               .categorie(categorieRepository
                       .findById(sousCategorieDto
                       .getIdCategorie())
                       .orElseThrow(()->new RuntimeException("categorie introuvable")))
               .libelle(sousCategorieDto.getLibelle())
               .description(sousCategorieDto.getDescription())
               .build();
   }
  public   List<SousCategorieDto> toDtoList(List<SousCategorie> sousCategories){
      return sousCategories.stream()
              .map(this::toDto)
              .collect(Collectors.toList());
    }
}
