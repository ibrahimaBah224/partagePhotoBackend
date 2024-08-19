package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovisionnementDto extends AbstractEntitieDto {
    private int idApprovisionnement;
    private int idProduit;
    private int idEntrepot;
    private int idFournisseur;
    private int montantTotal;
    private int quantite;
    private float prixUniteAchat;
    private float prixUniteVente;
    private Date datePeremption;
}
