package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.modele.Magasin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagasinMapper {
    MagasinDto toDto(Magasin magasin);
    Magasin toEntity(MagasinDto magasinDto);
    List<MagasinDto> toDtoList(List<Magasin> magasins);
}
