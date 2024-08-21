package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement,Integer> {
    List<Approvisionnement> findApprovisionnementByCreatedBy(int createdBy);
   /* List<Approvisionnement> findApprovisionnementByFournisseur(int idFournisseur);
    List<Approvisionnement> findApprovisionnementByEntrepot(int idEntrepot);
    List<Approvisionnement> findApprovisionnementByProduit(int idProduit);
*/

}
