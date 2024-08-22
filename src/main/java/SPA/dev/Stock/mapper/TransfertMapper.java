package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.Transfert;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransfertMapper {
    public TransfertDto toDto(Transfert transfert){
        return TransfertDto.builder()
                .idTransfert(transfert.getIdTransfert())
                .idProduit(transfert.getProduit().getIdProduit())
                .idMagasin(transfert.getMagasin().getId())
                .status(transfert.getStatus())
                .build();
    }
    public  Transfert toEntity(TransfertDto transfertDto, Produit produit, Magasin magasin){
        return Transfert.builder()
                .idTransfert(transfertDto.getIdTransfert())
                .produit(produit)
                .magasin(magasin)
                .status(transfertDto.getStatus())
                .build();
    }
    public List<TransfertDto> toDtoList(List<Transfert> transfert){
        return transfert.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
