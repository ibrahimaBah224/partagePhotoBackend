package SPA.dev.Stock.modele;

import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String nom;

    private String adresse;

    private String reference;
    @Column(nullable = false)
    private EnumTypeMagasin typeMagasin;

    @OneToOne(mappedBy = "magasin", cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "entrepot")
    private List<Approvisionnement> approvisionnements;

}
