package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
}
