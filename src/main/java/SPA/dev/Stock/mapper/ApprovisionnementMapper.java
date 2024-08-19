package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.modele.Approvisionnement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApprovisionnementMapper  {

    ApprovisionnementDto toDto(Approvisionnement approvisionnement);
    Approvisionnement toEntity(ApprovisionnementDto approvisionnementDto);
    List<ApprovisionnementDto> toDtoList(List<Approvisionnement> approvisionnements);
}

