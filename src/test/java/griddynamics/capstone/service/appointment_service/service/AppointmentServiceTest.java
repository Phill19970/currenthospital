package griddynamics.capstone.service.appointment_service.service;

import griddynamics.capstone.service.CounterService;
import griddynamics.capstone.service.appointment_service.domain.Appointment;
import griddynamics.capstone.service.appointment_service.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AppointmentService.class)
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testCreateOrUpdateAppointment() {
        Appointment appointment = new Appointment();
        // set properties of the appointment
        appointment.setPatientId(1L);
        appointment.setDoctorId(2L);
        appointment.setDate(LocalDateTime.now());
        appointment.setDetails("Details of the appointment");

        Appointment savedAppointment = appointmentService.createOrUpdateAppointment(appointment);

        assertNotNull(savedAppointment.getId());
        // assert other properties
        assertEquals(appointment.getPatientId(), savedAppointment.getPatientId());
        assertEquals(appointment.getDoctorId(), savedAppointment.getDoctorId());
        assertEquals(appointment.getDate(), savedAppointment.getDate());
        assertEquals(appointment.getDetails(), savedAppointment.getDetails());
    }

}
