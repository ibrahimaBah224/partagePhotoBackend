package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Entrepot;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement,Integer> {
    List<Approvisionnement> findApprovisionnementByCreatedBy(int createdBy);
    List<ApprovisionnementDto> findApprovisionnementByFournisseur(Fournisseur fournisseur);
    List<ApprovisionnementDto> findApprovisionnementByEntrepot(Entrepot entrepot);
    List<ApprovisionnementDto> findApprovisionnementByProduit(Produit produit);


}
