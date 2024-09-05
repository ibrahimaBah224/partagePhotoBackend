package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.*;
import SPA.dev.Stock.modele.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Mapper {
    public VenteInitDto toDto(VenteInit venteInit) {
        return VenteInitDto.builder()
                .id(venteInit.getId())
                .reference(venteInit.getReference())
                .idClient(venteInit.getClient().getIdClient()) // Extraire uniquement l'ID du client
                .status(venteInit.getStatus())
                .remise(venteInit.getRemise())
                .payementMode(venteInit.getPayementMode())

                .createdAt(venteInit.getCreatedAt())
                .updatedAt(venteInit.getUpdatedAt())
                .createdBy(venteInit.getCreatedBy())
                .updatedBy(venteInit.getUpdatedBy())
                .build();
    }

    public VenteInit toEntity(VenteInitDto venteInitDto, Client client) {
        return VenteInit.builder()
                .id(venteInitDto.getId())
                .reference(venteInitDto.getReference())
                .client(client) // Associer l'entité Client
                .status(venteInitDto.getStatus())
                .remise(venteInitDto.getRemise())
                .payementMode(venteInitDto.getPayementMode())
                .build();
    }

    public VenteDto toVenteDto(Vente vente) {
        return VenteDto.builder()
                .id(vente.getId())
                .idProduit(vente.getProduit().getIdProduit())  // Extraire l'ID du produit
                .venteInitId(vente.getVenteInit().getId()) // Extraire l'ID de VenteInit
                .quantite(vente.getQuantite())
                .prixVente(vente.getPrixVente())
                .createdAt(vente.getCreatedAt())
                .createdBy(vente.getCreatedBy())
                .updatedAt(vente.getUpdatedAt())
                .updatedBy(vente.getUpdatedBy())
                .build();
    }

    // Mapper VenteDto to Vente
    public Vente toVenteEntity(VenteDto venteDto, Produit produit, VenteInit venteInit) {
        return Vente.builder()
                .id(venteDto.getId())
                .produit(produit) // Associer l'entité Produit
                .venteInit(venteInit) // Associer l'entité VenteInit
                .quantite(venteDto.getQuantite())
                .prixVente(venteDto.getPrixVente())

                .build();
    }

    public CaisseDto toCaisseDto(Caisse caisse) {
        return CaisseDto.builder()
                .id(caisse.getId())
                .typePaiement(caisse.getTypePaiement())
                .typeOperation(caisse.getTypeOperation())
                .montant(caisse.getMontant())
                .motif(caisse.getMotif())
                .createdAt(caisse.getCreatedAt())
                .createdBy(caisse.getCreatedBy())
                .updatedAt(caisse.getUpdatedAt())
                .updatedBy(caisse.getUpdatedBy())
                .status(caisse.getStatus())
                .build();
    }

    public Caisse toCaisseEntity(CaisseDto caisse) {
        return Caisse.builder()
                .id(caisse.getId())
                .typePaiement(caisse.getTypePaiement())
                .typeOperation(caisse.getTypeOperation())
                .montant(caisse.getMontant())
                .motif(caisse.getMotif())
                .build();
    }
    public List<CaisseDto> toCaisseDtoList(List<Caisse> caisse) {
        return caisse.stream().map(this::toCaisseDto).collect(toList());
    }

    public Perte toPerteEntity(PerteDto perteDto, Approvisionnement approvisionnement){
        return Perte.builder()
                .id(perteDto.getId())
                .approvisionnement(approvisionnement)
                .quantitePerdu(perteDto.getQuantitePerdu())
                .prixUnitaire(perteDto.getPrixUnitaire())
                .build();
    }

    public PerteDto toPerteDto(Perte perte){
        return PerteDto.builder()
                .id(perte.getId())
                .idApprovisionnement(perte.getApprovisionnement().getIdApprovisionnement())
                .quantitePerdu(perte.getQuantitePerdu())
                .prixUnitaire(perte.getPrixUnitaire())
                .createdAt(perte.getCreatedAt())
                .updatedAt(perte.getUpdatedAt())
                .createdBy(perte.getCreatedBy())
                .updatedBy(perte.getUpdatedBy())
                .status(perte.getStatus())
                .build();
    }

    public RecyclageDto toRecyclageDto(Recyclage recyclage){
        return RecyclageDto.builder()
                .id(recyclage.getId())
                .idPerte(recyclage.getPerte().getId())
                .quantitePerdu(recyclage.getQuantitePerdu())
                .createdAt(recyclage.getCreatedAt())
                .createdBy(recyclage.getCreatedBy())
                .updatedAt(recyclage.getUpdatedAt())
                .updatedBy(recyclage.getUpdatedBy())
                .status(recyclage.getStatus())
                .build();

    }

    public Recyclage toRecyclageEntity(RecyclageDto recyclageDto, Perte perte){
        return Recyclage.builder()
                .id(recyclageDto.getId())
                .perte(perte)
                .quantitePerdu(recyclageDto.getQuantitePerdu())
                .build();
    }

    public AchatInitDto toAchatInitDto(AchatInit achatInit) {
        return AchatInitDto.builder()
                .id(achatInit.getId())
                .reference(achatInit.getReference())
                .status(achatInit.getStatus())
                .createdAt(achatInit.getCreatedAt())
                .updatedAt(achatInit.getUpdatedAt())
                .createdBy(achatInit.getCreatedBy())
                .updatedBy(achatInit.getUpdatedBy())
                .build();
    }
    public AchatInit toAchatInitEntity(AchatInitDto achatInitDto) {
        return AchatInit.builder()
                .id(achatInitDto.getId())
                .reference(achatInitDto.getReference())
                .status(achatInitDto.getStatus())
                .build();
    }
    public AchatPanierDto toAchatPanierDto(AchatPanier achatPanier) {
        return AchatPanierDto.builder()
                .id(achatPanier.getId())
                .idProduit(achatPanier.getProduit().getIdProduit()) // Mapping the `Produit` entity's ID
                .idAchatInit(achatPanier.getAchatInit().getId())   // Mapping the `AchatInit` entity's ID
                .quantite(achatPanier.getQuantite())
                .prixUnitaire(achatPanier.getPrixUnitaire())
                .status(achatPanier.getStatus())
                .createdAt(achatPanier.getCreatedAt())
                .updatedAt(achatPanier.getUpdatedAt())
                .createdBy(achatPanier.getCreatedBy())
                .updatedBy(achatPanier.getUpdatedBy())
                .build();
    }
    public AchatPanier toAchatPanierEntity(AchatPanierDto achatPanierDto, Produit produit, AchatInit achatInit) {
        return AchatPanier.builder()
                .id(achatPanierDto.getId())
                .produit(produit) // Mapping the full `Produit` entity
                .achatInit(achatInit) // Mapping the full `AchatInit` entity
                .quantite(achatPanierDto.getQuantite())
                .prixUnitaire(achatPanierDto.getPrixUnitaire())
                .status(achatPanierDto.getStatus())
                .build();
    }


}