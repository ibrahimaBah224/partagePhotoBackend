package SPA.dev.Stock.repository;

import SPA.dev.Stock.dto.FournisseurDto;
import SPA.dev.Stock.modele.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
    Optional<Fournisseur> findFournisseurByIdFournissseurAndCreatedBy(int idFournisseur,int createdBy);
    List<Fournisseur> findFournisseursByCreatedBy(int createdBy);

    Optional<Fournisseur> findByTel(String telephone);
}
