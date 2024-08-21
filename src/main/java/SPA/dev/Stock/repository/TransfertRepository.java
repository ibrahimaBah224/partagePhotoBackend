package SPA.dev.Stock.repository;

import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransfertRepository extends JpaRepository<Transfert,Integer> {
    List<Transfert> findTransfertsByMagasinAndStatus(Magasin magasin, StatusTransfertEnum status);

}
