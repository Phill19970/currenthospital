package griddynamics.capstone.service.billing_service.repository;

import griddynamics.capstone.service.billing_service.domain.Bill;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
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
    public void deleteById(Long id) {
        billsById.remove(id);
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
}