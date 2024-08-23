package SPA.dev.Stock.dto;

import SPA.dev.Stock.modele.Approvisionnement;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class PerteDto {
    private Long id;
    private int idApprovisionnement;
    private float quantitePerdu;
    private float prixUnitaire;

    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
}
