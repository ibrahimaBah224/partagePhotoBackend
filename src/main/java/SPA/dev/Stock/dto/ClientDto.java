package SPA.dev.Stock.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
}
