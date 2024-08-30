package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.modele.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement,Integer> {
    @Query("SELECT SUM(a.quantite) FROM Approvisionnement a WHERE a.produit.idProduit = :idProduit AND a.createdBy = :createdBy")
    Integer findTotalQuantityByProduitIdAndCreatedBy(@Param("idProduit") int idProduit, @Param("createdBy") int createdBy);
    List<Approvisionnement> findApprovisionnementByCreatedBy(int createdBy);
    List<ApprovisionnementDto> findApprovisionnementByFournisseurAndCreatedBy(Fournisseur fournisseur,int id);
    List<ApprovisionnementDto> findApprovisionnementByEntrepotAndCreatedBy(Magasin entrepot, int createdBy);
    @Query("SELECT a FROM Approvisionnement a WHERE a.produit = :produit")
    List<Approvisionnement> findApprovisionnementByProduit(@Param("produit") Produit produit);


}
