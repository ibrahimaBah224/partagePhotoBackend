package SPA.dev.Stock.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProduitDto  extends AbstractEntitieDto{

    private int idProduit;

    private String designation;
    private String reference;
    private int seuil;
    private String description;
    private String image;
    private int id_sousCategorie;

}
