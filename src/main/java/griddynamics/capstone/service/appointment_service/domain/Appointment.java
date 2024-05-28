package griddynamics.capstone.service.appointment_service.domain;

import griddynamics.capstone.service.patient_service.domain.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Long doctorId;
    private LocalDateTime date;
    private String details;

    // Default constructor
    public Appointment() {
    }

    // Constructor with parameters
    public Appointment(Long id, Long patientId, Long doctorId, LocalDateTime date, String details) {
        this.id = id;
        this.patient = new Patient();
        this.patient.setId(patientId);
        this.doctorId = doctorId;
        this.date = date;
        this.details = details;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPatientId(Long patientId) {
        if (this.patient == null) {
            this.patient = new Patient();
        }
        this.patient.setId(patientId);
    }

    public Long getPatientId() {
        return this.patient != null ? this.patient.getId() : null;
    }
}