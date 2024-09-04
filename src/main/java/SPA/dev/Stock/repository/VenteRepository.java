package SPA.dev.Stock.repository;

import SPA.dev.Stock.enumeration.EnumPayementMode;
import SPA.dev.Stock.enumeration.EnumVente;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.modele.Vente;
import SPA.dev.Stock.modele.VenteInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    /*@Query("SELECT SUM(v.quantite)" +
            " FROM Vente v JOIN v.produit a" +
            " WHERE a.idProduit = :idProduit AND v.status = :status AND v.createdBy = :createdBy")
    Integer findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
            @Param("idProduit") int idProduit,
            @Param("status") int status,
            @Param("createdBy") int createdBy);
    */

    /*@Query("SELECT SUM(v.quantite)" +
            " FROM Vente v JOIN v.produit a" +
            " WHERE a.idProduit = :idProduit  AND v.createdBy = :createdBy")
    Integer findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
            @Param("idProduit") int idProduit,
            @Param("createdBy") int createdBy);*/

    @Query("SELECT SUM(v.quantite)" +
            " FROM Vente v JOIN v.produit a" +
            " WHERE a.idProduit = :idProduit  AND ( v.createdBy = :createdBy OR v.user= :user)")
    Integer findTotalQuantitySoldByProduitIdStatusAndCreatedBy(
            @Param("idProduit") int idProduit,
            @Param("createdBy") int createdBy,
            @Param("user") User user);

    List<Vente> findByCreatedBy(Integer userId);

    Optional<Vente> findByIdAndCreatedBy(Long id, int currentUserId);



    List<Vente> findByStatus(EnumVente status);


    List<Vente> findByCreatedAtBefore(Date olderThanDate);

    @Query("SELECT v FROM Vente v WHERE v.venteInit.id = :venteInitId AND v.venteInit.status = :status")
    List<Vente> findByVenteInitIdAndStatus(@Param("venteInitId") int venteInitId, @Param("status") int status);

    @Query("SELECT v.produit.idProduit, v.produit.designation, v.prixVente, SUM(v.quantite) " +
            "FROM Vente v " +
            "WHERE v.venteInit.id = :venteInitId AND v.venteInit.status = :status " +
            "GROUP BY v.produit.idProduit, v.produit.designation, v.prixVente")
    List<Object[]> findQuantiteTotaleParProduit(@Param("venteInitId") int venteInitId, @Param("status") int status);

    Vente findByProduit(Produit produit
    );

    Vente findByProduitAndCreatedByAndVenteInit(Produit produit, int currentUserId, VenteInit venteInit);
}