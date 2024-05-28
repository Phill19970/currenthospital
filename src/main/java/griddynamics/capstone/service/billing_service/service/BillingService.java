package griddynamics.capstone.service.billing_service.service;

import griddynamics.capstone.service.billing_service.domain.Bill;
import griddynamics.capstone.service.billing_service.repository.BillRepository;
import griddynamics.capstone.service.billing_service.repository.BillRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class BillingService {
    private final BillRepository billRepository;

    public BillingService() {
        this.billRepository = new BillRepositoryImpl();
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
