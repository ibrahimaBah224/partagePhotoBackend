package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.CaisseDto;
import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.dto.VenteInitDto;
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
                .produitId(vente.getProduit().getIdProduit())  // Extraire l'ID du produit
                .venteInitId(vente.getVenteInit().getId()) // Extraire l'ID de VenteInit
                .quantite(vente.getQuantite())
                .prixVente(vente.getPrixVente())
                .payementMode(vente.getPayementMode())
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
}