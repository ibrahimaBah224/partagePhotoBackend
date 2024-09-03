package SPA.dev.Stock.repository;

import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.modele.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagasinRepository extends JpaRepository<Magasin, Integer>{
    Magasin findByUserId(int userId);
    Optional<Magasin> findByIdAndUserId(int id, int currentUserId);
    List<Magasin> findAllByIdOrCreatedBy(int currentUserId, int currentUserId1);

    Optional<Magasin> findByIdAndCreatedBy(int idMagasin, int currentUserId);

    Optional<Magasin> findByNom(String nom);

    Optional<Magasin> findByIdAndUserIdAndTypeMagasin(int id, int currentUserId, EnumTypeMagasin typeMagasin);

    Optional<Magasin> findByIdAndTypeMagasin(int id,EnumTypeMagasin typeMagasin);

    List<Magasin> findAllByCreatedByAndTypeMagasin(int currentUserId, EnumTypeMagasin typeMagasin);

    Optional<Magasin> findByIdAndCreatedByAndTypeMagasin(int id, int currentUserId, EnumTypeMagasin enumTypeMagasin);
}
