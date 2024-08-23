package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumPayementMode;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class VenteDto {
    private Long id;

    @NotNull
    private int idApprovisionnement;  // Remplacer Produit par produitId

    @NotNull
    private Long venteInitId;  // Remplacer VenteInit par venteInitId

    private int quantite;
    private double prixVente;

    private EnumPayementMode payementMode;

    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;

}
