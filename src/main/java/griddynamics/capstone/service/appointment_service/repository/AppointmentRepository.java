package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    void deleteById(Long id);
    List<Appointment> findAllByPatientId(Long patientId);
    List<Appointment> findAll();
}
