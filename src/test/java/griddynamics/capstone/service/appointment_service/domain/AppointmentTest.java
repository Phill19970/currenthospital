package griddynamics.capstone.service.appointment_service.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void getIdReturnsCorrectId() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        assertEquals(1L, appointment.getId());
    }

    @Test
    void getPatientIdReturnsCorrectPatientId() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(1L);
        assertEquals(1L, appointment.getPatientId());
    }

    @Test
    void getDoctorIdReturnsCorrectDoctorId() {
        Appointment appointment = new Appointment();
        appointment.setDoctorId(1L);
        assertEquals(1L, appointment.getDoctorId());
    }

    @Test
    void getDateReturnsCorrectDate() {
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment();
        appointment.setDate(now);
        assertEquals(now, appointment.getDate());
    }

    @Test
    void getDetailsReturnsCorrectDetails() {
        Appointment appointment = new Appointment();
        appointment.setDetails("Details");
        assertEquals("Details", appointment.getDetails());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(1L);
        assertTrue(appointment1.equals(appointment2));
    }

    @Test
    void equalsReturnsFalseForDifferentObject() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        assertFalse(appointment1.equals(appointment2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameObject() {
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(1L);
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
    }

    @Test
    void toStringReturnsCorrectString() {
        Appointment appointment = new Appointment(1L, 2L, 3L, LocalDateTime.now(), "Details");
        String expected = "Appointment{id=1, patientId=2, doctorId=3, date=" + appointment.getDate() + ", details='Details'}";
        assertEquals(expected, appointment.toString());
    }
}