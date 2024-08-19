package SPA.dev.Stock.dto;


import SPA.dev.Stock.enumeration.EnumEtatCommande;
import SPA.dev.Stock.enumeration.EnumStatus;
import SPA.dev.Stock.modele.Client;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Client client;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumStatus status;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumEtatCommande etatCommande;
    private String remise;
}