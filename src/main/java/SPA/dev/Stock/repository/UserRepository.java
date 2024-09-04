package SPA.dev.Stock.repository;

import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByTelephone(String telephone);
    List<User> findByRole(RoleEnumeration role);

    List<User> getUsersByRole(RoleEnumeration roleEnumeration);

    List<User> findAllByCreatedBy(int currentUserId);

    Optional<User> findByIdAndCreatedBy(int id, int currentUserId);
}