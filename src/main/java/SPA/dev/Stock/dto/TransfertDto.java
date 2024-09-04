package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransfertDto  extends AbstractEntitieDto{

    private int idTransfert;
    private String produit;
    private String magasin;
    private int quantite;
    private int quantiteRestante;
    private StatusTransfertEnum status;
}
