package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Perte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerteRepository extends JpaRepository<Perte, Long> {
    List<Perte> findAllByCreatedBy(int idUser);
    @Query("SELECT SUM(p.quantitePerdu) FROM Perte p WHERE p.approvisionnement.produit.idProduit = :idProduit AND p.createdBy = :createdBy GROUP BY p.approvisionnement.produit.idProduit")
    Float findTotalQuantitePerduByProduitAndCreatedBy(@Param("idProduit") Long idProduit, @Param("createdBy") int createdBy);
    @Query("SELECT SUM(p.quantitePerdu) FROM Perte p WHERE p.approvisionnement.produit.idProduit = :idProduit AND (p.createdBy = :createdBy OR p.approvisionnement.entrepot.id = :idEntrepot) GROUP BY p.approvisionnement.produit.idProduit")
    Float findTotalQuantitePerduByProduitAndCreatedByOrEntrepot(@Param("idProduit") Long idProduit, @Param("createdBy") int createdBy, @Param("idEntrepot") int idEntrepot);

}
