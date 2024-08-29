package SPA.dev.Stock.dto;
import lombok.*;
import org.springframework.core.io.Resource;

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
    private String sousCategorie;
    private Resource photo;
    private int quantite = 0;
}
