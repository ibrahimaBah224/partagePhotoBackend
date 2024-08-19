package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.modele.Transfert;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransfertMapper {
    TransfertDto toDto(Transfert transfert);
    Transfert toEntity(TransfertDto transfertDto);
    List<TransfertDto> toDtoList(List<Transfert> approvisionnements);
}
