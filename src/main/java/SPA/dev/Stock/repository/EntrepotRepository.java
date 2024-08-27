package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.Entrepot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntrepotRepository extends JpaRepository<Entrepot,Integer> {
    Optional<Entrepot> findEntrepotByIdEntrepotAndCreatedBy(int idEntrepot, int createdBy);
    List<Entrepot> findEntrepotsByCreatedBy(int createdBy);


}
