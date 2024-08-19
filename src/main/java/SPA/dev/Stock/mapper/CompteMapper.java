package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.CompteDto;
import SPA.dev.Stock.modele.Compte;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    Compte toCompte(CompteDto compteDto);
    CompteDto toCompteDto(Compte compte);
    List<CompteDto> toCompteDtoList(List<Compte> comptes);
}