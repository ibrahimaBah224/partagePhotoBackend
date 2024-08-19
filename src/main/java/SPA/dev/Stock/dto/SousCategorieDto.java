package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SousCategorieDto  extends AbstractEntitieDto{

    private int idSousCategorie;
    private String libelle;
    private String description;
    private int  idCategorie;

}
