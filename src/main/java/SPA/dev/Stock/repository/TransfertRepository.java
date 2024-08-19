package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfertRepository extends JpaRepository<Transfert,Integer> {
}
