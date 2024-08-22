package SPA.dev.Stock.dto;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ClientDto {
    private Long idClient;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
}
