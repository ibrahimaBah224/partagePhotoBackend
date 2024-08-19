package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagasinRepository extends JpaRepository<Magasin,Integer> {
}
