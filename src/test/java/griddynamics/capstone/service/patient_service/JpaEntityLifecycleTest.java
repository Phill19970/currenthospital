package griddynamics.capstone.service.patient_service;

import griddynamics.capstone.PatientServiceApplication;
import griddynamics.capstone.service.patient_service.domain.Patient;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest(classes = PatientServiceApplication.class)
public class JpaEntityLifecycleTest {

    @Autowired
    private PatientRepository patientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // Clean the database before each test
        patientRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        // Clean the database after each test
        patientRepository.deleteAll();
    }

    @Test
    public void testSaveParentWithoutId() {
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");

        // Using repository.save()
        Patient savedPatientRepo = patientRepository.save(patient);
        Assertions.assertNotNull(savedPatientRepo.getId());

        // Using entityManager.persist()
        Patient patientPersist = new Patient();
        patientPersist.setName("Jane Doe");
        patientPersist.setEmail("jane.doe@example.com");
        entityManager.persist(patientPersist);
        entityManager.flush();
        Assertions.assertNotNull(patientPersist.getId());

        // Using entityManager.merge()
        Patient patientMerge = new Patient();
        patientMerge.setName("Jim Doe");
        patientMerge.setEmail("jim.doe@example.com");
        Patient mergedPatient = entityManager.merge(patientMerge);
        entityManager.flush();
        Assertions.assertNotNull(mergedPatient.getId());
    }

}