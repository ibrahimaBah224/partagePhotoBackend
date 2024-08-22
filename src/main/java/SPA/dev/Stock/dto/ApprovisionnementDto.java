package SPA.dev.Stock.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
