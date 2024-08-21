package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Approvisionnement extends AbstractEntitie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idApprovisionnement")
    private int idApprovisionnement;

    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idEntrepot", nullable = true)
    private Entrepot entrepot;

    @ManyToOne
    @JoinColumn(name = "idFournisseur", nullable = false)
    private Fournisseur fournisseur;

    private int montantTotal;
    private  int quantite;
    private float prixUniteAchat;
    private float prixUniteVente;
    private Date datePeremption;


}

