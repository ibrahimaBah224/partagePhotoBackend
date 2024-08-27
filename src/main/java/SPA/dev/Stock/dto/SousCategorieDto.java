package SPA.dev.Stock.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SousCategorieDto  {

    private int idSousCategorie;
    private String libelle;
    private String description;
    private int  idCategorie;

}
