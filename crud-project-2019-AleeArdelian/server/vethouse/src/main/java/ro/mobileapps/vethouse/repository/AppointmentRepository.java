package ro.mobileapps.vethouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.mobileapps.vethouse.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
}
