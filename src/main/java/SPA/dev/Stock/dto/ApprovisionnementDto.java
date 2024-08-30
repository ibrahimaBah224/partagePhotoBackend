package SPA.dev.Stock.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovisionnementDto  {
    private int idApprovisionnement;
    private String produit;
    private String entrepot;
    private String fournisseur;
    private int montantTotal;
    private int quantite;
    private float prixUniteAchat;
    private float prixUniteVente;
    private Date datePeremption;
    private int createdBy;
    private Date createdAt;
    private int updatedBy;
    private Date updatedAt;
    private  int statut;
}
