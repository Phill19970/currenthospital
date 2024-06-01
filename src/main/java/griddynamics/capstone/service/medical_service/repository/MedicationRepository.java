package griddynamics.capstone.service.medical_service.repository;

import griddynamics.capstone.service.medical_service.domain.Medication;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
    Medication save(Medication medication);
    Optional<Medication> findById(Long id);
    void deleteById(Long id);
    List<Medication> findAll();
}