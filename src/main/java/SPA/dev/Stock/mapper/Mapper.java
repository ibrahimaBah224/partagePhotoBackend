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
                .etatCommande(venteInit.getEtatCommande())
                .remise(venteInit.getRemise())
                .build();
    }

    public VenteInit toEntity(VenteInitDto venteInitDto, Client client) {
        return VenteInit.builder()
                .id(venteInitDto.getId())
                .reference(venteInitDto.getReference())
                .client(client) // Associer l'entité Client
                .status(venteInitDto.getStatus())
                .etatCommande(venteInitDto.getEtatCommande())
                .remise(venteInitDto.getRemise())
                .build();
    }

    public VenteDto toVenteDto(Vente vente) {
        return VenteDto.builder()
                .id(vente.getId())
                .idApprovisionnement(vente.getApprovisionnement().getIdApprovisionnement())  // Extraire l'ID du produit
                .venteInitId(vente.getVenteInit().getId()) // Extraire l'ID de VenteInit
                .quantite(vente.getQuantite())
                .prixVente(vente.getPrixVente())
                .payementMode(vente.getPayementMode())
                .build();
    }

    // Mapper VenteDto to Vente
    public Vente toVenteEntity(VenteDto venteDto, Approvisionnement approvisionnement, VenteInit venteInit) {
        return Vente.builder()
                .id(venteDto.getId())
                .approvisionnement(approvisionnement) // Associer l'entité Produit
                .venteInit(venteInit) // Associer l'entité VenteInit
                .quantite(venteDto.getQuantite())
                .prixVente(venteDto.getPrixVente())
                .payementMode(venteDto.getPayementMode())
                .build();
    }

    public CaisseDto toCaisseDto(Caisse caisse) {
        return CaisseDto.builder()
                .id(caisse.getId())
                .typePaiement(caisse.getTypePaiement())
                .typeOperation(caisse.getTypeOperation())
                .montant(caisse.getMontant())
                .motif(caisse.getMotif())
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
}