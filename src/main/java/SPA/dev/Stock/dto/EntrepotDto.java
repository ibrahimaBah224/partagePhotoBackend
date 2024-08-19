package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class EntrepotDto extends AbstractEntitieDto{

    private int idEntrepot;
    private String nom;
    private String adresse;

}
