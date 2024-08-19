package SPA.dev.Stock.repository;


import SPA.dev.Stock.modele.VenteInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteInitRepository extends JpaRepository<VenteInit, Long> {

}