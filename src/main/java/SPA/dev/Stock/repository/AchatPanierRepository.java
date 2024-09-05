package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.AchatPanierDto;
import SPA.dev.Stock.modele.AchatInit;
import SPA.dev.Stock.modele.AchatPanier;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface AchatPanierRepository extends JpaRepository<AchatPanier,Long> {
    List<AchatPanier> findByCreatedBy(Integer userId);

    Optional<AchatPanier> findByIdAndCreatedBy(Long id, int currentUserId);

    AchatPanier findByProduitAndCreatedByAndAchatInit(Produit produit, int currentUserId, AchatInit achatInit);

    List<AchatPanier> findByAchatInitIdAndStatus(int idAchatVente, int i);
}
