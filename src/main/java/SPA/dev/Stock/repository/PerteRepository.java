package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Perte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerteRepository extends JpaRepository<Perte, Long> {
    List<Perte> findAllByCreatedBy(int idUser);
}
