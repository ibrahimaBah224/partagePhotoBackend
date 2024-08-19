package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Fournisseur extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFournissseur")
    private int idFournissseur;
    private String nom;
    private String prenom;
    private String societe;
    private String tel;
    private String email;
    private String adresse;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<Approvisionnement> approvisionnements;
}