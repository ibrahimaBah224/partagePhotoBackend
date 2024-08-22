package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Entrepot;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.Produit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApprovisionnementMapper  {

    public   ApprovisionnementDto toDto(Approvisionnement approvisionnement){
        return ApprovisionnementDto.builder()
                .idApprovisionnement(approvisionnement.getIdApprovisionnement())
                .idEntrepot(approvisionnement.getEntrepot().getIdEntrepot())
                .idProduit(approvisionnement.getProduit().getIdProduit())
                .idFournisseur(approvisionnement.getFournisseur().getIdFournissseur())
                .montantTotal(approvisionnement.getMontantTotal())
                .prixUniteAchat(approvisionnement.getPrixUniteAchat())
                .prixUniteVente(approvisionnement.getPrixUniteVente())
                .quantite(approvisionnement.getQuantite())
                .datePeremption(approvisionnement.getDatePeremption())
                .build();
    }
    public  Approvisionnement toEntity(ApprovisionnementDto approvisionnementDto, Produit produit, Entrepot entrepot, Fournisseur fournisseur){
        return Approvisionnement.builder()
                .idApprovisionnement(approvisionnementDto.getIdApprovisionnement())
                .entrepot(entrepot)
                .produit(produit)
                .fournisseur(fournisseur)
                .montantTotal(approvisionnementDto.getMontantTotal())
                .prixUniteAchat(approvisionnementDto.getPrixUniteAchat())
                .prixUniteVente(approvisionnementDto.getPrixUniteVente())
                .quantite(approvisionnementDto.getQuantite())
                .datePeremption(approvisionnementDto.getDatePeremption())
                .build();
    }
    public List<ApprovisionnementDto> toDtoList(List<Approvisionnement> approvisionnements){
        return approvisionnements.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

