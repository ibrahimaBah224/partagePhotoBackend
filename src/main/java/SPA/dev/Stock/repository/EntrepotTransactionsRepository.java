package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.EntrepotTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepotTransactionsRepository extends JpaRepository<EntrepotTransactions,Integer> {
}
