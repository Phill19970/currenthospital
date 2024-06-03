package griddynamics.capstone.service.medical_service.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String instructions;

    // Default constructor
    public Medication() {
    }

    // Parameterized constructor for initializing medication objects
    public Medication(Long id, String name, String dosage, String instructions) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    // Getters and setters for all properties
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // Override equals and hashCode methods focusing on the 'id' field
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Override toString method for easy logging and debugging
    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}