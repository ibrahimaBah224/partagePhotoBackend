package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.EntrepotDto;
import SPA.dev.Stock.modele.Entrepot;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntrepotMapper {
    EntrepotDto toDto(Entrepot entrepot);
    Entrepot toEntity(EntrepotDto entrepotDto);
    List<EntrepotDto> toDtoList(List<Entrepot> entrepots);
}
