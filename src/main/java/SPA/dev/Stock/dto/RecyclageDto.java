package SPA.dev.Stock.dto;

import SPA.dev.Stock.modele.Perte;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class RecyclageDto {
    private Long id;
    private Long idPerte;
    private float quantitePerdu;
    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
    private int status;

}
