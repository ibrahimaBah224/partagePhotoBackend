package SPA.dev.Stock.modele;


import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfert extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransfert;

    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idMagasin", nullable = false)
    private Magasin magasin;

    private int quantite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransfertEnum status ;

}
