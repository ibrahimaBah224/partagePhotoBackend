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
    List<ApprovisionnementDto> findApprovisionnementByFournisseurAndCreatedBy(Fournisseur fournisseur,int id);
    List<ApprovisionnementDto> findApprovisionnementByEntrepotAndCreatedBy(Entrepot entrepot,int createdBy);
    List<ApprovisionnementDto> findApprovisionnementByProduit(Produit produit);


}
