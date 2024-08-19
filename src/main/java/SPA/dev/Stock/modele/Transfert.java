package SPA.dev.Stock.modele;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Transfert extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransfert")
    private int idTransfert;
    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idMagasin", nullable = false)
    private Magasin magasin;
}
