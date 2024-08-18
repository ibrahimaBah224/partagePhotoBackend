package SPA.dev.Stock.repository;

import SPA.dev.Stock.modele.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByTelephone(String telephone);
}