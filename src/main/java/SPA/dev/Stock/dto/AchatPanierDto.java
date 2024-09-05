package SPA.dev.Stock.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class AchatPanierDto {
    private Long id;

    private int idProduit;

    private Long idAchatInit;

    private int quantite;

    private double prixUnitaire;
    private int status;
    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
}
