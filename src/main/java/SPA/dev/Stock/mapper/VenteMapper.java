package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.modele.Vente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    Vente toVente(VenteDto venteDto);
    VenteDto toVenteDto(Vente vente);
    List<VenteDto> toVenteDtoList(List<Vente> venteDtoList);
}