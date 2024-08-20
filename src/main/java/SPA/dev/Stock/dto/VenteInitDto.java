package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumEtatCommande;
import SPA.dev.Stock.enumeration.EnumStatus;
import SPA.dev.Stock.modele.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class VenteInitDto {
    private Long id;
    private String reference;

    // Remplacer Client par idClient
    private Long idClient;

    @NotNull
    private EnumStatus status;
    @NotNull
    private EnumEtatCommande etatCommande;
    private String remise;
}
