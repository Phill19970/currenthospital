package griddynamics.capstone.service.billing_service.repository;

import griddynamics.capstone.service.billing_service.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByPatientId(Long patientId);

    List<Bill> findAllByPatientId(Long patientId);
}