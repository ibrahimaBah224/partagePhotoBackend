package SPA.dev.Stock.modele;

import SPA.dev.Stock.enumeration.EnumPayementMode;
import SPA.dev.Stock.enumeration.EnumVente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;
    @ManyToOne
    private VenteInit venteInit;
    private int quantite;
    private double prixVente;
    private int status;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private int createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private int updatedBy;
}