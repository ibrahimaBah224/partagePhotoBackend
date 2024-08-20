package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumPayementMode;
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
    private int produitId;  // Remplacer Produit par produitId

    @NotNull
    private Long venteInitId;  // Remplacer VenteInit par venteInitId

    private int quantite;
    private double prixVente;

    private EnumPayementMode payementMode;
}
