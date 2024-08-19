package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement,Integer> {
}
