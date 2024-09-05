package SPA.dev.Stock.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchatProduitDto {
    private long idVente;
    private String designation;
    private double  prixUnitaire;
    private int quantite;
    private long id;
}
