package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit,Integer> {
    List<Produit> findProduitsByCreatedBy(int createdBy);
    Optional<Produit> findProduitByIdProduitAndCreatedBy(int idCategorie, int createdBy);
    Produit findByDesignation(String designation);

}
