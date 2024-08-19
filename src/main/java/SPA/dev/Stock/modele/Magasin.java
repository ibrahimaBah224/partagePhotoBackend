package SPA.dev.Stock.modele;

import SPA.dev.Stock.service.UserService;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
public class Magasin {
    @Transient
    private UserService userService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String nom;
    private String adresse;
    private String reference;
    @OneToOne
    @JoinColumn(name = "admin_id")
    private User user;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "updated_by")
    private int updatedBy;

    @PrePersist
    protected void onCreate() {
        createdBy = userService.getCurrentUserId();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedBy = userService.getCurrentUserId();
    }
}
