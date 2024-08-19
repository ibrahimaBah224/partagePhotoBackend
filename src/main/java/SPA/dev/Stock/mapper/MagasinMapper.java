package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.modele.Magasin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagasinMapper {


    MagasinDto magasinToMagasinDTO(Magasin magasin);

    Magasin magasinDTOToMagasin(MagasinDto magasinDTO);

    List<MagasinDto> magasinsToMagasinDTOs(List<Magasin> magasins);

}
