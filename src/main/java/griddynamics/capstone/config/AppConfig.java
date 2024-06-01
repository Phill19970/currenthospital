package griddynamics.capstone.config;

import griddynamics.capstone.service.appointment_service.repository.AppointmentRepository;
import griddynamics.capstone.service.appointment_service.repository.AppointmentRepositoryImpl;
import griddynamics.capstone.service.appointment_service.service.AppointmentService;
import griddynamics.capstone.service.billing_service.repository.BillRepository;
import griddynamics.capstone.service.billing_service.repository.BillRepositoryImpl;
import griddynamics.capstone.service.billing_service.service.BillingService;
import griddynamics.capstone.service.medical_service.repository.MedicationRepository;
import griddynamics.capstone.service.medical_service.repository.MedicationRepositoryImpl;
import griddynamics.capstone.service.medical_service.service.MedicationService;
import griddynamics.capstone.service.patient_service.repository.PatientRepository;
import griddynamics.capstone.service.patient_service.repository.PatientRepositoryImpl;
import griddynamics.capstone.service.patient_service.service.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public AppointmentRepository appointmentRepository() {
        return new AppointmentRepositoryImpl();
    }

    @Bean
    public AppointmentService appointmentService() {
        return new AppointmentService(appointmentRepository());
    }

    @Bean
    public BillRepository billRepository() {
        return new BillRepositoryImpl();
    }

    @Bean
    public BillingService billingService() {
        return new BillingService(billRepository());
    }

    @Bean
    public MedicationRepository medicationRepository() {
        return new MedicationRepositoryImpl();
    }

    @Bean
    public MedicationService medicationService() {
        return new MedicationService(medicationRepository());
    }

    @Bean
    public PatientRepository patientRepository(DataSource dataSource) {
        return new PatientRepositoryImpl(dataSource);
    }

    @Bean
    public PatientService patientService(PatientRepository patientRepository, DataSource dataSource) {
        return new PatientService(patientRepository, dataSource);
    }
}