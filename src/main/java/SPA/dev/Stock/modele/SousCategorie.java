package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SousCategorie extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSousCategorie;

    private String libelle;

    private String description;

    @ManyToOne
    @JoinColumn(name = "idCategorie",nullable = false)
    private Categorie categorie;

}
