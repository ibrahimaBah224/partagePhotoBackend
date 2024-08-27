package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface TransfertRepository extends JpaRepository<Transfert,Integer> {

    List<Transfert> findTransfertsByMagasinAndStatus(Magasin magasin, StatusTransfertEnum status);
    List<TransfertDto> findTransfertsByMagasin(Magasin magasin);

    Optional<Transfert> findTransfertByIdTransfertAndCreatedBy(int idTransfert, int createdBy);


    @Query("SELECT SUM(t.quantite) FROM Transfert t WHERE t.produit.idProduit = :idProduit AND t.createdBy = :createdBy")
    Integer findTotalQuantiteByProduitAndCreatedBy(
            @Param("idProduit") int idProduit,
            @Param("createdBy") int createdBy
    );
    
}
