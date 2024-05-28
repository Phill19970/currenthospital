package griddynamics.capstone.service;

import griddynamics.capstone.MyAppApplication;
import griddynamics.capstone.service.appointment_service.domain.Appointment;
import griddynamics.capstone.service.appointment_service.repository.AppointmentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = MyAppApplication.class)
@ActiveProfiles("test")
public class AppointmentServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        // Clean the database before each test
        appointmentRepository.findAll().forEach(appointment -> appointmentRepository.deleteById(appointment.getId()));
    }

    @AfterEach
    public void tearDown() {
        // Clean the database after each test
        appointmentRepository.findAll().forEach(appointment -> appointmentRepository.deleteById(appointment.getId()));
    }

    @Test
    public void testPersistAndFind() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(1L);
        appointment.setDoctorId(1L);
        appointment.setDate(LocalDateTime.now());
        appointment.setDetails("Test Details");
        entityManager.persist(appointment);
        entityManager.flush();

        Appointment found = entityManager.find(Appointment.class, appointment.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Test Details", found.getDetails());
    }

    @Test
    public void testMerge() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(1L);
        appointment.setDoctorId(1L);
        appointment.setDate(LocalDateTime.now());
        appointment.setDetails("Initial Details");
        entityManager.persist(appointment);
        entityManager.flush();

        appointment.setDetails("Updated Details");
        Appointment merged = entityManager.merge(appointment);
        entityManager.flush();

        Appointment found = entityManager.find(Appointment.class, merged.getId());
        Assertions.assertEquals("Updated Details", found.getDetails());
    }

    @Test
    public void testRemove() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(1L);
        appointment.setDoctorId(1L);
        appointment.setDate(LocalDateTime.now());
        appointment.setDetails("Test Details");
        entityManager.persist(appointment);
        entityManager.flush();

        entityManager.remove(appointment);
        entityManager.flush();

        Appointment found = entityManager.find(Appointment.class, appointment.getId());
        Assertions.assertNull(found);
    }

    @Test
    public void testRepositorySaveAndFind() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(1L);
        appointment.setDoctorId(1L);
        appointment.setDate(LocalDateTime.now());
        appointment.setDetails("Repository Test");
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findAllByPatientId(1L);
        Assertions.assertFalse(appointments.isEmpty());
        Assertions.assertEquals("Repository Test", appointments.get(0).getDetails());
    }
}