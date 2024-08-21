package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class TransfertDto  extends AbstractEntitieDto{

    private int idTransfert;
    private int idProduit;
    private int idMagasin;
    private StatusTransfertEnum status;
}
