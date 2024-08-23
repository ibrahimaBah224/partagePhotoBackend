package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Recyclage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclageRepository  extends JpaRepository<Recyclage, Long> {
    List<Recyclage> findAllByCreatedBy(int userId);
}
