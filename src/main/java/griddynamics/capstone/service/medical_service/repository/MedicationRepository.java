package griddynamics.capstone.service.medical_service.repository;

import griddynamics.capstone.service.medical_service.domain.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
}