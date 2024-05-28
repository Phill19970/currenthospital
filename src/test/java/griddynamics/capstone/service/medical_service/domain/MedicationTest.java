package griddynamics.capstone.service.medical_service.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    @Test
    void getIdReturnsCorrectId() {
        Medication medication = new Medication();
        medication.setId(1L);
        assertEquals(1L, medication.getId());
    }

    @Test
    void getNameReturnsCorrectName() {
        Medication medication = new Medication();
        medication.setName("Medicine");
        assertEquals("Medicine", medication.getName());
    }

    @Test
    void getDosageReturnsCorrectDosage() {
        Medication medication = new Medication();
        medication.setDosage("2 tablets");
        assertEquals("2 tablets", medication.getDosage());
    }

    @Test
    void getInstructionsReturnsCorrectInstructions() {
        Medication medication = new Medication();
        medication.setInstructions("After meals");
        assertEquals("After meals", medication.getInstructions());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        Medication medication1 = new Medication();
        medication1.setId(1L);
        Medication medication2 = new Medication();
        medication2.setId(1L);
        assertTrue(medication1.equals(medication2));
    }

    @Test
    void equalsReturnsFalseForDifferentObject() {
        Medication medication1 = new Medication();
        medication1.setId(1L);
        Medication medication2 = new Medication();
        medication2.setId(2L);
        assertFalse(medication1.equals(medication2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameObject() {
        Medication medication1 = new Medication();
        medication1.setId(1L);
        Medication medication2 = new Medication();
        medication2.setId(1L);
        assertEquals(medication1.hashCode(), medication2.hashCode());
    }

    @Test
    void toStringReturnsCorrectString() {
        Medication medication = new Medication(1L, "Medicine", "2 tablets", "After meals");
        String expected = "Medication{id=1, name='Medicine', dosage='2 tablets', instructions='After meals'}";
        assertEquals(expected, medication.toString());
    }
}