package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private final Map<Long, Appointment> appointmentsById = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId(idGenerator.incrementAndGet());
        }
        appointmentsById.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return Optional.ofNullable(appointmentsById.get(id));
    }

    @Override
    public void deleteById(Long id) {
        appointmentsById.remove(id);
    }

    @Override
    public List<Appointment> findAllByPatientId(Long patientId) {
        return appointmentsById.values().stream()
                .filter(appointment -> Objects.equals(appointment.getPatientId(), patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointmentsById.values());
    }
}
