package griddynamics.capstone.service.medical_service.repository;

import griddynamics.capstone.service.medical_service.domain.Medication;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
    public void deleteById(Long id) {
        medicationsById.remove(id);
    }

    @Override
    public List<Medication> findAll() {
        return new ArrayList<>(medicationsById.values());
    }
}
