package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumEtatCommande;
import SPA.dev.Stock.enumeration.EnumStatus;
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
    private Long clientId;
    @NotNull
    private EnumStatus status;
    @NotNull
    private EnumEtatCommande etatCommande;
    private String remise;
}
