package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Fournisseur extends AbstractEntitie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFournissseur")
    private int idFournissseur;
    private String nom;
    private String prenom;
    private String societe;
    private String tel;
    private String email;
    private String adresse;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<Approvisionnement> approvisionnements;


}