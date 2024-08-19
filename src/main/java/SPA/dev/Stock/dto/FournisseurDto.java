package SPA.dev.Stock.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FournisseurDto extends AbstractEntitieDto{

    private int idFournissseur;
    private String nom;
    private String prenom;
    private String societe;
    private String tel;
    private String email;
    private String adresse;
}