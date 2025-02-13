package SPA.dev.Stock.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Categorie extends AbstractEntitie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategorie;
    private String libelle;
    private String description;
}
