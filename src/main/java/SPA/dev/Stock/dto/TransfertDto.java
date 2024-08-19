package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransfertDto  extends AbstractEntitieDto{

    private int idTransfert;
    private int idProduit;
    private int idMagasin;
}
