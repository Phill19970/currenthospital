package griddynamics.capstone.service.patient_service.service;

import griddynamics.capstone.service.patient_service.domain.Patient;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import griddynamics.capstone.service.patient_service.repository.PatientRepositoryImpl;
import griddynamics.capstone.service.patient_service.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {
    private PatientService patientService;



    @BeforeEach
    public void setUp() throws Exception {
        String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        DataSource dataSource = new HikariDataSource(config);

        PatientRepository patientRepository = new PatientRepositoryImpl(dataSource);
        patientService = new PatientService(patientRepository, dataSource);
    }

    

@Test
public void testDeletePatient() {
    Patient patient = new Patient(null, "John Doe", "john.doe@example.com", "No history");
    Patient savedPatient = patientService.createOrUpdatePatient(patient);

    patientService.deletePatient(savedPatient.getId());

    assertThrows(NoSuchElementException.class, () -> patientService.getPatientById(savedPatient.getId()));
}

}