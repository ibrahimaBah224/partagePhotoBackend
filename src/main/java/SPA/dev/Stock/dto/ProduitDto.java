package SPA.dev.Stock.dto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDto  extends AbstractEntitieDto{

    private int idProduit;

    private String designation;
    private String reference;
    private int seuil;
    private String description;
    private String image;
    private int id_sousCategorie;

}
