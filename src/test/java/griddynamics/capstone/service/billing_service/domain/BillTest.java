package griddynamics.capstone.service.billing_service.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void getIdReturnsCorrectId() {
        Bill bill = new Bill();
        bill.setId(1L);
        assertEquals(1L, bill.getId());
    }

    @Test
    void getPatientIdReturnsCorrectPatientId() {
        Bill bill = new Bill();
        bill.setPatientId(1L);
        assertEquals(1L, bill.getPatientId());
    }

    @Test
    void getDateReturnsCorrectDate() {
        LocalDate now = LocalDate.now();
        Bill bill = new Bill();
        bill.setDate(now);
        assertEquals(now, bill.getDate());
    }

    @Test
    void getTotalAmountReturnsCorrectAmount() {
        Bill bill = new Bill();
        bill.setTotalAmount(100.0);
        assertEquals(100.0, bill.getTotalAmount());
    }

    @Test
    void getStatusReturnsCorrectStatus() {
        Bill bill = new Bill();
        bill.setTotalAmount(100.0);
        bill.setStatus(Bill.BillStatus.PAID);
        assertEquals(Bill.BillStatus.PAID, bill.getStatus());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        Bill bill1 = new Bill();
        bill1.setId(1L);
        Bill bill2 = new Bill();
        bill2.setId(1L);
        assertTrue(bill1.equals(bill2));
    }

    @Test
    void equalsReturnsFalseForDifferentObject() {
        Bill bill1 = new Bill();
        bill1.setId(1L);
        Bill bill2 = new Bill();
        bill2.setId(2L);
        assertFalse(bill1.equals(bill2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameObject() {
        Bill bill1 = new Bill();
        bill1.setId(1L);
        Bill bill2 = new Bill();
        bill2.setId(1L);
        assertEquals(bill1.hashCode(), bill2.hashCode());
    }

    @Test
    void toStringReturnsCorrectString() {
        Bill bill = new Bill(1L, 2L, LocalDate.now(), 100.0, Bill.BillStatus.PAID);
        String expected = "Bill{id=1, patientId=2, date=" + bill.getDate() + ", totalAmount=100.0, status=PAID}";
        assertEquals(expected, bill.toString());
    }
}