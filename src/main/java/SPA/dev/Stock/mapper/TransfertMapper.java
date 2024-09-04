package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.Transfert;

import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransfertMapper {

    private final ProduitRepository produitRepository;
    private final MagasinRepository magasinRepository;
    public TransfertDto toDto(Transfert transfert){
        return TransfertDto.builder()
                .idTransfert(transfert.getIdTransfert())
                .produit(transfert.getProduit().getDesignation())
                .magasin(transfert.getMagasin().getNom())
                .quantite(transfert.getQuantite())
                .status(transfert.getStatus())
                .build();
    }
    public  Transfert toEntity(TransfertDto transfertDto){
        return Transfert.builder()
                .idTransfert(transfertDto.getIdTransfert())
                .produit(produitRepository.findById(Integer.valueOf(transfertDto
                        .getProduit()))
                        .orElseThrow(()->new RuntimeException("produit introuvable")))
                .magasin(magasinRepository.findById(Integer.valueOf(transfertDto
                        .getMagasin()))
                        .orElseThrow(()->new RuntimeException("idMagasin introuvable")))
                .quantite(transfertDto.getQuantite())
                .status(transfertDto.getStatus())
                .build();
    }
    public List<TransfertDto> toDtoList(List<Transfert> transfert){
        return transfert.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Transfert> toEntityList(List<TransfertDto> transfertdto){
        return transfertdto.stream()
                .map(transfertDto -> {

                   transfertDto.setMagasin(String.valueOf(magasinRepository.findByNom(transfertDto.getMagasin()).
                           orElseThrow(()->new RuntimeException("not found")).getId()));
                   transfertDto.setProduit(String.valueOf(produitRepository.findByDesignation(transfertDto.getProduit()).getIdProduit()));
                    Transfert transfert = toEntity(transfertDto);

                    return transfert;
                })
                .collect(Collectors.toList());
    }
}
