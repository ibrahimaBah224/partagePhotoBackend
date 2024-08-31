package SPA.dev.Stock.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitVenteDto {
    private long idVente;
    private String designation;
    private double  prixUnitaire;
    private int quantite = 0;
}
