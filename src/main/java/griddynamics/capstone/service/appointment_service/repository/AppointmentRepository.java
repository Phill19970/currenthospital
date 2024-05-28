package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;

import java.util.List;
import java.util.Optional;
import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, QueryByExampleExecutor<Appointment> {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    void deleteById(Long id);
    List<Appointment> findAllByPatientId(Long patientId);
    List<Appointment> findAll();
}
