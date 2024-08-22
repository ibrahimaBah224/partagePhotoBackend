package SPA.dev.Stock.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategorieDto extends AbstractEntitieDto  {

    private int idCategorie;

    private String libelle;

    private String description;
}
