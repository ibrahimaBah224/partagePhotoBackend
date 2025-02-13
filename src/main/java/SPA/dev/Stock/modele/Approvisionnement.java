package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Approvisionnement  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idApprovisionnement")
    private int idApprovisionnement;

    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idEntrepot", nullable = true)
    private Magasin entrepot;

    @ManyToOne
    @JoinColumn(name = "idFournisseur", nullable = false)
    private Fournisseur fournisseur;

    private int montantTotal;
    private  int quantite;
    private float prixUniteAchat;
    private float prixUniteVente;
    private Date datePeremption;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;


    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private int createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private int updatedBy;

    @Column(columnDefinition = "integer default 1", nullable = false)
    private int statut = 1;


}

