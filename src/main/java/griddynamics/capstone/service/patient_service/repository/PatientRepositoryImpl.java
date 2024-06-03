package griddynamics.capstone.service.patient_service.repository;

import griddynamics.capstone.service.patient_service.domain.Patient;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final HikariDataSource dataSource;

    public PatientRepositoryImpl(DataSource dataSource) {
        if (!(dataSource instanceof HikariDataSource)) {
            throw new IllegalArgumentException("DataSource must be an instance of HikariDataSource");
        }
        this.dataSource = (HikariDataSource) dataSource;
    }

    @Override
    public Patient save(Patient patient) {
        String sql = "INSERT INTO patients (name, email, medical_history) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getEmail());
            statement.setString(3, patient.getMedicalHistory());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating patient failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating patient failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("id"));
                patient.setName(resultSet.getString("name"));
                patient.setEmail(resultSet.getString("email"));
                patient.setMedicalHistory(resultSet.getString("medical_history"));
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM patients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("id"));
                patient.setName(resultSet.getString("name"));
                patient.setEmail(resultSet.getString("email"));
                patient.setMedicalHistory(resultSet.getString("medical_history"));
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Patient entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Patient> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM patients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("id"));
                patient.setName(resultSet.getString("name"));
                patient.setEmail(resultSet.getString("email"));
                patient.setMedicalHistory(resultSet.getString("medical_history"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public List<Patient> findAll(Sort sort) {
        throw new UnsupportedOperationException("Sorting not implemented");
    }

    @Override
    public List<Patient> findAllById(Iterable<Long> ids) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Long id : ids) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Patient patient = new Patient();
                    patient.setId(resultSet.getLong("id"));
                    patient.setName(resultSet.getString("name"));
                    patient.setEmail(resultSet.getString("email"));
                    patient.setMedicalHistory(resultSet.getString("medical_history"));
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM patients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public <S extends Patient> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add((S) save(entity));
        }
        return result;
    }

    @Override
    public void flush() {
    }

    @Override
    public <S extends Patient> S saveAndFlush(S entity) {
        return (S) save(entity);
    }

    @Override
    public <S extends Patient> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Patient> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        deleteAllById(ids);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public Patient getOne(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public Patient getById(Long id) {
        return findById(id).orElse(null);
    }

    @Override
    public <S extends Patient> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient> long count(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public <S extends Patient, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Example queries not implemented");
    }

    @Override
    public Page<Patient> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Paging not implemented");
    }
}