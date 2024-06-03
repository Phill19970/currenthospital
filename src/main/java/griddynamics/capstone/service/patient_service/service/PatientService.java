package griddynamics.capstone.service.patient_service.service;

import griddynamics.capstone.service.patient_service.domain.Patient;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, DataSource dataSource) {
        this.patientRepository = patientRepository;
    }

    public Patient createOrUpdatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> findPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(NoSuchElementException::new);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}