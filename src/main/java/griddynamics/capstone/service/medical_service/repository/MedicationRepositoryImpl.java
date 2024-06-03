package griddynamics.capstone.service.medical_service.repository;

import griddynamics.capstone.service.medical_service.domain.Medication;
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
public class MedicationRepositoryImpl implements MedicationRepository {
    private final Map<Long, Medication> medicationsById = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Medication save(Medication medication) {
        if (medication.getId() == null) {
            medication.setId(idGenerator.incrementAndGet());
        }
        medicationsById.put(medication.getId(), medication);
        return medication;
    }

    @Override
    public Optional<Medication> findById(Long id) {
        return Optional.ofNullable(medicationsById.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return medicationsById.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        medicationsById.remove(id);
    }

    @Override
    public void delete(Medication entity) {
        medicationsById.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(medicationsById::remove);
    }

    @Override
    public void deleteAll(Iterable<? extends Medication> entities) {
        entities.forEach(entity -> medicationsById.remove(entity.getId()));
    }

    @Override
    public void deleteAll() {
        medicationsById.clear();
    }

    @Override
    public List<Medication> findAll() {
        return new ArrayList<>(medicationsById.values());
    }

    @Override
    public List<Medication> findAll(Sort sort) {
        throw new UnsupportedOperationException("Sorting not implemented");
    }

    @Override
    public Page<Medication> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Paging not implemented");
    }

    @Override
    public List<Medication> findAllById(Iterable<Long> ids) {
        List<Medication> result = new ArrayList<>();
        for (Long id : ids) {
            Medication medication = medicationsById.get(id);
            if (medication != null) {
                result.add(medication);
            }
        }
        return result;
    }

    @Override
    public long count() {
        return medicationsById.size();
    }

    @Override
    public <S extends Medication> List<S> saveAll(Iterable<S> entities) {
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
    public <S extends Medication> S saveAndFlush(S entity) {
        return (S) save(entity);
    }

    @Override
    public <S extends Medication> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Medication> entities) {
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
    public Medication getOne(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public Medication getById(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public <S extends Medication> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication> long count(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Medication, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }
}