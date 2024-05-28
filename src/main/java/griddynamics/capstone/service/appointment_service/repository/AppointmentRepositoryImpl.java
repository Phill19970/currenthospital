package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppointmentRepositoryImpl implements AppointmentRepository, JpaRepository<Appointment, Long> {
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
    public boolean existsById(Long id) {
        return appointmentsById.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        appointmentsById.remove(id);
    }

    @Override
    public void delete(Appointment entity) {
        appointmentsById.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Appointment> entities) {
        for (Appointment entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        appointmentsById.clear();
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

    @Override
    public List<Appointment> findAllById(Iterable<Long> ids) {
        List<Appointment> result = new ArrayList<>();
        for (Long id : ids) {
            Appointment appointment = appointmentsById.get(id);
            if (appointment != null) {
                result.add(appointment);
            }
        }
        return result;
    }

    @Override
    public long count() {
        return appointmentsById.size();
    }

    @Override
    public List<Appointment> findAll(Sort sort) {
        return appointmentsById.values().stream()
                .sorted((a1, a2) -> {
                    for (Sort.Order order : sort) {
                        int comparison = compareAppointments(a1, a2, order);
                        if (comparison != 0) {
                            return order.isAscending() ? comparison : -comparison;
                        }
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<Appointment> findAll(Pageable pageable) {
        List<Appointment> sortedAppointments = findAll(pageable.getSort());
        int total = sortedAppointments.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        List<Appointment> content = sortedAppointments.subList(start, end);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public <S extends Appointment> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            save(entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public void flush() {
        // No-op for in-memory implementation
    }

    @Override
    public <S extends Appointment> S saveAndFlush(S entity) {
        S savedEntity = (S) save(entity);
        flush();
        return savedEntity;
    }

    @Override
    public <S extends Appointment> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> savedEntities = saveAll(entities);
        flush();
        return savedEntities;
    }

    @Override
    public void deleteAllInBatch(Iterable<Appointment> entities) {
        for (Appointment entity : entities) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAllInBatch() {
        appointmentsById.clear();
    }

    @Override
    public Appointment getOne(Long id) {
        return appointmentsById.get(id);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentsById.get(id);
    }

    @Override
    public <S extends Appointment> Optional<S> findOne(Example<S> example) {
        return appointmentsById.values().stream()
                .filter(appointment -> example.getProbe().equals(appointment))
                .map(appointment -> (S) appointment)
                .findFirst();
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example) {
        return appointmentsById.values().stream()
                .filter(appointment -> example.getProbe().equals(appointment))
                .map(appointment -> (S) appointment)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example, Sort sort) {
        return findAll(sort).stream()
                .filter(appointment -> example.getProbe().equals(appointment))
                .map(appointment -> (S) appointment)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends Appointment> Page<S> findAll(Example<S> example, Pageable pageable) {
        List<S> filteredAppointments = findAll(example);
        int total = filteredAppointments.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        List<S> content = filteredAppointments.subList(start, end);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public <S extends Appointment> long count(Example<S> example) {
        return findAll(example).size();
    }

    @Override
    public <S extends Appointment> boolean exists(Example<S> example) {
        return findOne(example).isPresent();
    }



    @Override
    public void deleteInBatch(Iterable<Appointment> entities) {
        deleteAllInBatch(entities);
    }

    private int compareAppointments(Appointment a1, Appointment a2, Sort.Order order) {
        switch (order.getProperty()) {
            case "id":
                return Long.compare(a1.getId(), a2.getId());
            case "patientId":
                return Long.compare(a1.getPatientId(), a2.getPatientId());
            case "date":
                return a1.getDate().compareTo(a2.getDate());
            // Add more cases as needed for other properties
            default:
                throw new IllegalArgumentException("Unknown property: " + order.getProperty());
        }
    }
}