package SPA.dev.Stock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends AbstractEntitieDto{
    private Integer idClient;

    private String nom;

    private String email;

    private String telephone;
}
