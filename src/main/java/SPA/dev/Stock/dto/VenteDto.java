package SPA.dev.Stock.dto;

import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.VenteInit;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class VenteDto {
    private Long id;
    @NotNull
    private Produit produit;
    @NotNull
    private VenteInit venteInit;
    private int quantite;
    private double prixVente;
}