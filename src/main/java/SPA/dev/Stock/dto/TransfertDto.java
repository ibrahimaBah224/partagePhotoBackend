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
    private int idProduit;
    private int idMagasin;
    private int quantite;
    private StatusTransfertEnum status;
    private int quantité;
}
