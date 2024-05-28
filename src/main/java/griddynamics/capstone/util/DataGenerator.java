package griddynamics.capstone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class DataGenerator {
    private static final String INSERT_PATIENT_SQL = "INSERT INTO patients (name, email, medical_history) VALUES (?, ?, ?)";
    private static final String INSERT_APPOINTMENT_SQL = "INSERT INTO appointments (patient_id, doctor_id, date, details) VALUES (?, ?, ?, ?)";
    private static final int NUM_PATIENTS = 1000000;
    private static final int NUM_APPOINTMENTS_PER_PATIENT = 10;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/mydb", "user", "password")) {
            Random random = new Random();
            for (int i = 0; i < NUM_PATIENTS; i++) {
                try (PreparedStatement patientStatement = connection.prepareStatement(INSERT_PATIENT_SQL)) {
                    patientStatement.setString(1, "Patient " + i);
                    patientStatement.setString(2, "patient" + i + "@example.com");
                    patientStatement.setString(3, "Medical history of patient " + i);
                    patientStatement.executeUpdate();

                    for (int j = 0; j < NUM_APPOINTMENTS_PER_PATIENT; j++) {
                        try (PreparedStatement appointmentStatement = connection.prepareStatement(INSERT_APPOINTMENT_SQL)) {
                            appointmentStatement.setLong(1, i + 1);
                            appointmentStatement.setLong(2, random.nextInt(100) + 1);
                            appointmentStatement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(random.nextInt(365))));
                            appointmentStatement.setString(4, "Details of appointment " + j);
                            appointmentStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}