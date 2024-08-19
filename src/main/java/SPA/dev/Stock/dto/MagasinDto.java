package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MagasinDto extends AbstractEntitieDto{

    private int idMagasin;
    private String nom;
    private String adresse;

}
