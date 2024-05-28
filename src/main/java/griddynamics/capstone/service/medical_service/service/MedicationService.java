package griddynamics.capstone.service.medical_service.service;

import griddynamics.capstone.service.medical_service.domain.Medication;
import griddynamics.capstone.service.medical_service.repository.MedicationRepository;
import griddynamics.capstone.service.medical_service.repository.MedicationRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class MedicationService {
    private final MedicationRepository medicationRepository;

    public MedicationService() {
        this.medicationRepository = new MedicationRepositoryImpl();
    }

    public Medication createOrUpdateMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Optional<Medication> findMedicationById(Long id) {
        return medicationRepository.findById(id);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }

    public List<Medication> findAllMedications() {
        return medicationRepository.findAll();
    }
}
