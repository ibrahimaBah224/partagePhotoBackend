package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumEtatCommande;
import SPA.dev.Stock.enumeration.EnumPayementMode;
import SPA.dev.Stock.enumeration.EnumStatus;
import SPA.dev.Stock.modele.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

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

    private String remise;
    private EnumPayementMode payementMode;

    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
    private int status;

}
