package griddynamics.capstone.service.patient_service.repository;

import griddynamics.capstone.service.patient_service.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Patient save(Patient patient); // Create or Update
    Optional<Patient> findById(Long id);
    Optional<Patient> findByEmail(String email);
    void deleteById(Long id);
    List<Patient> findAll();
}