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
                .idProduit(transfert.getProduit().getIdProduit())
                .idMagasin(transfert.getMagasin().getId())
                .quantite(transfert.getQuantite())
                .status(transfert.getStatus())
                .build();
    }
    public  Transfert toEntity(TransfertDto transfertDto){
        return Transfert.builder()
                .idTransfert(transfertDto.getIdTransfert())
                .produit(produitRepository.findById(transfertDto
                        .getIdProduit())
                        .orElseThrow(()->new RuntimeException("produit introuvable")))
                .magasin(magasinRepository.findById(transfertDto
                        .getIdMagasin())
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
}
