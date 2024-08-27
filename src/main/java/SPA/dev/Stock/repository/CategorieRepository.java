package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {
    List<Categorie> findCategorieByCreatedBy(int createdBy);
    Optional<Categorie> findCategorieByIdCategorieAndCreatedBy(int idCategorie, int createdBy);

}
