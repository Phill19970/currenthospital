package griddynamics.capstone.service.appointment_service.service;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import griddynamics.capstone.service.appointment_service.repository.AppointmentRepository;
import griddynamics.capstone.service.appointment_service.repository.AppointmentRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepositoryImpl();
    }

    public Appointment createOrUpdateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> findAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAllAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findAllByPatientId(patientId);
    }
}
