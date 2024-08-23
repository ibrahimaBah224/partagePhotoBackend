package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produit extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduit")
    private int idProduit;

    private String designation;
    @Column(unique = true)
    private String reference;
    private int seuil;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "idSousCategorie", nullable = false)
    private SousCategorie sousCategorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Approvisionnement> approvisionnements;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Transfert> transferts;


}
