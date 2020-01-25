package ro.mobileapps.vethouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.mobileapps.vethouse.model.Doctor;
import ro.mobileapps.vethouse.model.User;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByEmail(String email);

}
