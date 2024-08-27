package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Entrepot;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.repository.EntrepotRepository;
import SPA.dev.Stock.repository.FournisseurRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApprovisionnementMapper  {

    private final EntrepotRepository entrepotRepository;
    private final ProduitRepository produitRepository;
    private final FournisseurRepository fournisseurRepository;
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
    public  Approvisionnement toEntity(ApprovisionnementDto approvisionnementDto){
        return Approvisionnement.builder()
                .idApprovisionnement(approvisionnementDto.getIdApprovisionnement())
                .entrepot(entrepotRepository
                        .findById(approvisionnementDto.getIdEntrepot())
                        .orElseThrow(()->new RuntimeException("idEntrepot introuvable")))
                .produit(produitRepository
                        .findById(approvisionnementDto.getIdProduit())
                        .orElseThrow(()->new RuntimeException("idProduit introuvable")))
                .fournisseur(fournisseurRepository
                        .findById(approvisionnementDto.getIdFournisseur())
                        .orElseThrow(()->new RuntimeException("idFournisseur introuvable")))
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

