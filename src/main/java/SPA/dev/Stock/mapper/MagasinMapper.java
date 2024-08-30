package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.modele.Magasin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class MagasinMapper {
    public MagasinDto magasinToMagasinDTO(Magasin magasin){
        return  MagasinDto.builder()
                .id(magasin.getId())
                .typeMagasin(String.valueOf(magasin.getTypeMagasin()))
                .nom(magasin.getNom())
                .adresse(magasin.getAdresse())
                .reference(magasin.getReference())
                .build();
    }
    public Magasin magasinDTOToMagasin(MagasinDto magasinDto){
        return Magasin.builder()
               .id(magasinDto.getId())
               .typeMagasin(EnumTypeMagasin.valueOf(magasinDto.getTypeMagasin()))
               .nom(magasinDto.getNom())
               .adresse(magasinDto.getAdresse())
               .reference(magasinDto.getReference())
               .build();
    }
    public List<MagasinDto> magasinsToMagasinDTOs(List<Magasin> magasins) {
        return magasins.stream().map(this::magasinToMagasinDTO).collect(toList());
    }
}
