package griddynamics.capstone.service.billing_service.service;

import griddynamics.capstone.service.billing_service.domain.Bill;
import griddynamics.capstone.service.billing_service.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {
    private final BillRepository billRepository;

    @Autowired
    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill createOrUpdateBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Optional<Bill> findBillById(Long id) {
        return billRepository.findById(id);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> findAllBillsByPatientId(Long patientId) {
        return billRepository.findAllByPatientId(patientId);
    }
}