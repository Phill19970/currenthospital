package griddynamics.capstone.service.patient_service.service;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import griddynamics.capstone.service.patient_service.domain.Patient;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import griddynamics.capstone.service.patient_service.repository.PatientRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PatientService {
    private final PatientRepository patientRepository;
    private final DataSource dataSource;

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

    public Patient getPatientAndAppointments(Long patientId) {
        String getPatientSql = "SELECT * FROM Patients WHERE id = ?";
        String getAppointmentsSql = "SELECT * FROM Appointments WHERE patient_id = ?";
        Patient patient = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement patientStatement = connection.prepareStatement(getPatientSql);
             PreparedStatement appointmentStatement = connection.prepareStatement(getAppointmentsSql)) {
            patientStatement.setLong(1, patientId);
            ResultSet patientResultSet = patientStatement.executeQuery();
            if (patientResultSet.next()) {
                patient = new Patient();
                patient.setId(patientResultSet.getLong("id"));
                patient.setName(patientResultSet.getString("name"));
                patient.setEmail(patientResultSet.getString("email"));
                patient.setMedicalHistory(patientResultSet.getString("medical_history"));
            }
            appointmentStatement.setLong(1, patientId);
            ResultSet appointmentResultSet = appointmentStatement.executeQuery();
            while (appointmentResultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(appointmentResultSet.getLong("id"));
                appointment.setPatientId(appointmentResultSet.getLong("patient_id"));
                appointment.setDoctorId(appointmentResultSet.getLong("doctor_id"));
                appointment.setDate(appointmentResultSet.getTimestamp("date").toLocalDateTime());
                appointment.setDetails(appointmentResultSet.getString("details"));
                patient.getAppointments().add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public Patient getPatientAndAppointmentsRepeatableRead(Long patientId) {
        String getPatientSql = "SELECT * FROM Patients WHERE id = ?";
        String getAppointmentsSql = "SELECT * FROM Appointments WHERE patient_id = ?";
        Patient patient = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // set isolation level
            connection.setAutoCommit(false); // start transaction
            try (PreparedStatement patientStatement = connection.prepareStatement(getPatientSql);
                 PreparedStatement appointmentStatement = connection.prepareStatement(getAppointmentsSql)) {
                patientStatement.setLong(1, patientId);
                ResultSet patientResultSet = patientStatement.executeQuery();
                if (patientResultSet.next()) {
                    patient = new Patient();
                    patient.setId(patientResultSet.getLong("id"));
                    patient.setName(patientResultSet.getString("name"));
                    patient.setEmail(patientResultSet.getString("email"));
                    patient.setMedicalHistory(patientResultSet.getString("medical_history"));
                }
                appointmentStatement.setLong(1, patientId);
                ResultSet appointmentResultSet = appointmentStatement.executeQuery();
                while (appointmentResultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(appointmentResultSet.getLong("id"));
                    appointment.setPatientId(appointmentResultSet.getLong("patient_id"));
                    appointment.setDoctorId(appointmentResultSet.getLong("doctor_id"));
                    appointment.setDate(appointmentResultSet.getTimestamp("date").toLocalDateTime());
                    appointment.setDetails(appointmentResultSet.getString("details"));
                    patient.getAppointments().add(appointment);
                }
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
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return patient;
    }
}