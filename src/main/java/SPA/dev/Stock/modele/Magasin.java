package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Magasin extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMagasin")
    private Integer idMagasin;

    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "magasin", cascade = CascadeType.ALL)
    private List<Transfert> transferts;
}
