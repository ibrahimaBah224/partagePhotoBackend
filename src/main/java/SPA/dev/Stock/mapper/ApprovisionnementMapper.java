package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.FournisseurRepository;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApprovisionnementMapper  {

    private final ProduitRepository produitRepository;
    private final FournisseurRepository fournisseurRepository;
    private final MagasinRepository magasinRepository;
    private final ApprovisionnementRepository approvisionnementRepository;

    public   ApprovisionnementDto toDto(Approvisionnement approvisionnement){
        return ApprovisionnementDto.builder()
                .idApprovisionnement(approvisionnement.getIdApprovisionnement())
                .entrepot(approvisionnement.getEntrepot().getNom())
                .produit(approvisionnement.getProduit().getDesignation())
                .fournisseur(approvisionnement.getFournisseur().getNom()+" "+approvisionnement.getFournisseur().getPrenom())
                .montantTotal(approvisionnement.getMontantTotal())
                .prixUniteAchat(approvisionnement.getPrixUniteAchat())
                .prixUniteVente(approvisionnement.getPrixUniteVente())
                .quantite(approvisionnement.getQuantite())
                .datePeremption(approvisionnement.getDatePeremption())
                .statut(approvisionnement.getStatut())
                .createdAt(approvisionnement.getCreatedAt())
                .createdBy(approvisionnement.getCreatedBy())
                .updatedAt(approvisionnement.getUpdatedAt())
                .updatedBy(approvisionnement.getUpdatedBy())
                .build();
    }
    public  Approvisionnement toEntity(ApprovisionnementDto approvisionnementDto){
        return Approvisionnement.builder()
                .idApprovisionnement(approvisionnementDto.getIdApprovisionnement())
                .entrepot(magasinRepository
                        .findByIdAndTypeMagasin(Integer.parseInt(approvisionnementDto.getEntrepot()), EnumTypeMagasin.ENTREPOT)
                        .orElseThrow(()->new RuntimeException("idEntrepot introuvable")))
                .produit(produitRepository
                        .findById(Integer.valueOf(approvisionnementDto.getProduit()))
                        .orElseThrow(()->new RuntimeException("idProduit introuvable")))
                .fournisseur(fournisseurRepository
                        .findById(Integer.valueOf(approvisionnementDto.getFournisseur()))
                        .orElseThrow(()->new RuntimeException("idFournisseur introuvable")))
                .montantTotal(approvisionnementDto.getMontantTotal())
                .prixUniteAchat(approvisionnementDto.getPrixUniteAchat())
                .prixUniteVente(approvisionnementDto.getPrixUniteVente())
                .quantite(approvisionnementDto.getQuantite())
                .datePeremption(approvisionnementDto.getDatePeremption())
                .statut(approvisionnementDto.getStatut())
                .createdAt(approvisionnementDto.getCreatedAt())
                .createdBy(approvisionnementDto.getCreatedBy())
                .updatedAt(approvisionnementDto.getUpdatedAt())
                .updatedBy(approvisionnementDto.getUpdatedBy())
                .build();
    }
    public List<ApprovisionnementDto> toDtoList(List<Approvisionnement> approvisionnements){
        return approvisionnements.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

