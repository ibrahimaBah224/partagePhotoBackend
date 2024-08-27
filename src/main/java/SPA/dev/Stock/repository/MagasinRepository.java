package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagasinRepository extends JpaRepository<Magasin, Integer>{

    Magasin findByUserId(int userId);
    Optional<Magasin> findByIdAndUserId(int id, int currentUserId);
    List<Magasin> findAllByIdOrCreatedBy(int currentUserId, int currentUserId1);
}
