package ro.mobileapps.vethouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.mobileapps.vethouse.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    //Optional<User> deleteByEmail(String email);
    Optional<User> findByEmail(String email);
}
