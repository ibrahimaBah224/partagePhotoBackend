package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.FournisseurDto;
import SPA.dev.Stock.modele.Fournisseur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FournisseurMapper  {
    FournisseurDto toDto(Fournisseur fournisseur);
    Fournisseur toEntity(FournisseurDto fournisseur);
    List<FournisseurDto> toDtoList(List<Fournisseur> fournisseurs);
}