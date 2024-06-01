package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
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
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointmentsById.values()) {
            if (appointment.getPatientId().equals(patientId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointmentsById.values());
    }
}