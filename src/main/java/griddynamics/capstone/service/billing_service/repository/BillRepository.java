package griddynamics.capstone.service.billing_service.repository;

import griddynamics.capstone.service.billing_service.domain.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Bill save(Bill bill);
    Optional<Bill> findById(Long id);
    void deleteById(Long id);
    List<Bill> findAllByPatientId(Long patientId);
    List<Bill> findAll();
}
