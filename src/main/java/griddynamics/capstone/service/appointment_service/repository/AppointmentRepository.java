package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientId(Long patientId);

    List<Appointment> findByPatientId(Long patientId);
}