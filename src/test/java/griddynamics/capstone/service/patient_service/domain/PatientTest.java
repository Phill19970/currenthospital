package griddynamics.capstone.service.patient_service.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void creatingPatientWithAllFieldsShouldSetAllFields() {
        Patient patient = new Patient(1L, "John Doe", "john.doe@example.com", "No known allergies");

        assertEquals(1L, patient.getId());
        assertEquals("John Doe", patient.getName());
        assertEquals("john.doe@example.com", patient.getEmail());
        assertEquals("No known allergies", patient.getMedicalHistory());
    }

    @Test
    void creatingPatientWithNoFieldsShouldHaveNullFields() {
        Patient patient = new Patient();

        assertNull(patient.getId());
        assertNull(patient.getName());
        assertNull(patient.getEmail());
        assertNull(patient.getMedicalHistory());
    }

    @Test
    void settingFieldsOnPatientShouldUpdateFields() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");
        patient.setMedicalHistory("No known allergies");

        assertEquals(1L, patient.getId());
        assertEquals("John Doe", patient.getName());
        assertEquals("john.doe@example.com", patient.getEmail());
        assertEquals("No known allergies", patient.getMedicalHistory());
    }

    @Test
    void patientsWithSameIdShouldBeEqual() {
        Patient patient1 = new Patient(1L, "John Doe", "john.doe@example.com", "No known allergies");
        Patient patient2 = new Patient(1L, "Jane Doe", "jane.doe@example.com", "No known allergies");

        assertEquals(patient1, patient2);
    }

    @Test
    void patientsWithDifferentIdShouldNotBeEqual() {
        Patient patient1 = new Patient(1L, "John Doe", "john.doe@example.com", "No known allergies");
        Patient patient2 = new Patient(2L, "Jane Doe", "jane.doe@example.com", "No known allergies");

        assertNotEquals(patient1, patient2);
    }

    @Test
    void patientsWithSameIdShouldHaveSameHashCode() {
        Patient patient1 = new Patient(1L, "John Doe", "john.doe@example.com", "No known allergies");
        Patient patient2 = new Patient(1L, "Jane Doe", "jane.doe@example.com", "No known allergies");

        assertEquals(patient1.hashCode(), patient2.hashCode());
    }

    @Test
    void patientsWithDifferentIdShouldHaveDifferentHashCode() {
        Patient patient1 = new Patient(1L, "John Doe", "john.doe@example.com", "No known allergies");
        Patient patient2 = new Patient(2L, "Jane Doe", "jane.doe@example.com", "No known allergies");

        assertNotEquals(patient1.hashCode(), patient2.hashCode());
    }
}