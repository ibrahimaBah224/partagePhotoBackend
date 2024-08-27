package SPA.dev.Stock.repository;

import SPA.dev.Stock.enumeration.EnumPayementMode;
import SPA.dev.Stock.enumeration.EnumVente;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    @Query("SELECT SUM(v.quantite)" +
            " FROM Vente v JOIN v.produit a" +
            " WHERE a.idProduit = :idProduit AND v.status = :status AND v.createdBy = :createdBy")
    Integer findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
            @Param("idProduit") int idProduit,
            @Param("status") EnumVente status,
            @Param("createdBy") int createdBy);
    List<Vente> findByCreatedBy(Integer userId);

    Optional<Vente> findByIdAndCreatedBy(Long id, int currentUserId);



    List<Vente> findByStatus(EnumVente status);


    List<Vente> findByCreatedAtBefore(Date olderThanDate);


}