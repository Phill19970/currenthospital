package griddynamics.capstone.service.appointment_service.repository;

import griddynamics.capstone.service.appointment_service.domain.Appointment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

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
        ids.forEach(appointmentsById::remove);
    }

    @Override
    public void deleteAll(Iterable<? extends Appointment> entities) {
        entities.forEach(entity -> appointmentsById.remove(entity.getId()));
    }

    @Override
    public void deleteAll() {
        appointmentsById.clear();
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

    @Override
    public List<Appointment> findAll(Sort sort) {
        throw new UnsupportedOperationException("Sorting not implemented");
    }

    @Override
    public Page<Appointment> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Paging not implemented");
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
    public <S extends Appointment> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add((S) save(entity));
        }
        return result;
    }

    @Override
    public void flush() {
        // No-op for now
    }

    @Override
    public <S extends Appointment> S saveAndFlush(S entity) {
        return (S) save(entity);
    }

    @Override
    public <S extends Appointment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Appointment> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        deleteAllById(ids);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public Appointment getOne(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public Appointment getById(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public <S extends Appointment> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment> long count(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Appointment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return null;
    }
}