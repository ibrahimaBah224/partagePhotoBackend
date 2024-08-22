package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaisseRepository extends JpaRepository<Caisse,Long> {
}
