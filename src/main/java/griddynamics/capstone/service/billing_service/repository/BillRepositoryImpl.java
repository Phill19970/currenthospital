package griddynamics.capstone.service.billing_service.repository;

import griddynamics.capstone.service.billing_service.domain.Bill;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class BillRepositoryImpl implements BillRepository {
    private final Map<Long, Bill> billsById = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Bill save(Bill bill) {
        if (bill.getId() == null) {
            bill.setId(idGenerator.incrementAndGet());
        }
        billsById.put(bill.getId(), bill);
        return bill;
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return Optional.ofNullable(billsById.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return billsById.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        billsById.remove(id);
    }

    @Override
    public void delete(Bill entity) {
        billsById.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(billsById::remove);
    }

    @Override
    public void deleteAll(Iterable<? extends Bill> entities) {
        entities.forEach(entity -> billsById.remove(entity.getId()));
    }

    @Override
    public void deleteAll() {
        billsById.clear();
    }

    @Override
    public List<Bill> findByPatientId(Long patientId) {
        return null;
    }

    @Override
    public List<Bill> findAllByPatientId(Long patientId) {
        return billsById.values().stream()
                .filter(bill -> Objects.equals(bill.getPatientId(), patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Bill> findAll() {
        return new ArrayList<>(billsById.values());
    }

    @Override
    public List<Bill> findAll(Sort sort) {
        throw new UnsupportedOperationException("Sorting not implemented");
    }

    @Override
    public Page<Bill> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Paging not implemented");
    }

    @Override
    public List<Bill> findAllById(Iterable<Long> ids) {
        List<Bill> result = new ArrayList<>();
        for (Long id : ids) {
            Bill bill = billsById.get(id);
            if (bill != null) {
                result.add(bill);
            }
        }
        return result;
    }

    @Override
    public long count() {
        return billsById.size();
    }

    @Override
    public <S extends Bill> List<S> saveAll(Iterable<S> entities) {
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
    public <S extends Bill> S saveAndFlush(S entity) {
        return (S) save(entity);
    }

    @Override
    public <S extends Bill> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Bill> entities) {
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
    public Bill getOne(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public Bill getById(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public <S extends Bill> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill> long count(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Bill, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }
}