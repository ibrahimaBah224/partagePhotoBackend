package SPA.dev.Stock.repository;


import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.modele.VenteInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenteInitRepository extends JpaRepository<VenteInit, Long> {

    List<VenteInit> findAllByCreatedBy(int currentUserId);
    <T>ScopedValue<T> findByIdAndCreatedBy(Long id, int currentUserId);
}