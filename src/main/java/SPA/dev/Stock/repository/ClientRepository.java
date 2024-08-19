package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}

