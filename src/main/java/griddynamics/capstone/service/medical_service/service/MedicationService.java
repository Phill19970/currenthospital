package griddynamics.capstone.service.medical_service.service;

import griddynamics.capstone.service.medical_service.domain.Medication;
import griddynamics.capstone.service.medical_service.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
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