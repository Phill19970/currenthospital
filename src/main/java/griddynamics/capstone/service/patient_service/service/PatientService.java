package griddynamics.capstone.service.patient_service.service;

import griddynamics.capstone.service.patient_service.domain.Patient;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final DataSource dataSource;

    @Autowired
    public PatientService(PatientRepository patientRepository, DataSource dataSource) {
        this.patientRepository = patientRepository;
        this.dataSource = dataSource;
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

    public void deletePatientWithoutTransaction(Long patientId) {
        String deletePatientSql = "DELETE FROM Patients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deletePatientSql)) {
            statement.setLong(1, patientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatientWithTransaction(Long patientId) {
        String deleteAppointmentsSql = "DELETE FROM Appointments WHERE patient_id = ?";
        String deletePatientSql = "DELETE FROM Patients WHERE id = ?";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false); // start transaction

            // delete appointments
            try (PreparedStatement statement = connection.prepareStatement(deleteAppointmentsSql)) {
                statement.setLong(1, patientId);
                statement.executeUpdate();
            }

            // delete patient
            try (PreparedStatement statement = connection.prepareStatement(deletePatientSql)) {
                statement.setLong(1, patientId);
                statement.executeUpdate();
            }

            connection.commit(); // end transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // rollback transaction in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // restore default mode
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}