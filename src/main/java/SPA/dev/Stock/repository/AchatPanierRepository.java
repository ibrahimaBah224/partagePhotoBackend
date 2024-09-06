package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.AchatPanierDto;
import SPA.dev.Stock.modele.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface AchatPanierRepository extends JpaRepository<AchatPanier,Long> {
    List<AchatPanier> findByCreatedBy(Integer userId);

    Optional<AchatPanier> findByIdAndCreatedBy(Long id, int currentUserId);

    AchatPanier findByProduitAndCreatedByAndAchatInit(Produit produit, int currentUserId, AchatInit achatInit);

    //List<AchatPanier> findByAchatInitIdAndStatus(int idAchatVente, int i);

    @Query("SELECT a FROM AchatPanier a WHERE a.achatInit.id = :achatInitId AND a.achatInit.status = :status")
    List<AchatPanier> findByAchatInitIdAndStatus(@Param("achatInitId") int achatInitId, @Param("status") int status);
}
