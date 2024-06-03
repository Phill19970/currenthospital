CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    medical_history TEXT
);

CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    patient_id BIGINT,
    doctor_id BIGINT,
    date TIMESTAMP,
    details TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);