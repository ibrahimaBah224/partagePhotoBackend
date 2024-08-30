package SPA.dev.Stock.modele;

import SPA.dev.Stock.enumeration.EnumOperationEntrepot;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EntrepotTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrepot", nullable = false)
    private Magasin entrepot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    private int quantite;

    @Enumerated(EnumType.STRING)
    private EnumOperationEntrepot operation;

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
