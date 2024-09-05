package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.AchatInit;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.VenteInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchatInitRepository extends JpaRepository<AchatInit,Long> {
    List<AchatInit> findAllByCreatedBy(int currentUserId);

    AchatInit findByIdAndCreatedBy(Long id, int currentUserId);
}
