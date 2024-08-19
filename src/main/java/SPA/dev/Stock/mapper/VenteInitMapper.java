package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.modele.VenteInit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenteInitMapper {
    VenteInit toVenteInit(VenteInitDto venteInitDto);
    VenteInitDto toVenteInitDto(VenteInit venteInit);
    List<VenteInitDto> toVenteInitDtoList(List<VenteInit> venteInit);
}