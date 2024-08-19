package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Entrepot extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrepot;

    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "entrepot", cascade = CascadeType.ALL)
    private List<Approvisionnement> approvisionnements;


}
