package SPA.dev.Stock.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MagasinDto {

    private  int id;
    private String nom;
    private String adresse;
    private String reference;
    private String typeMagasin;
}
