package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByProduitId(Long produitId);
}