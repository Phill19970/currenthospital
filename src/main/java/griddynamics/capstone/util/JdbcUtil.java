package griddynamics.capstone.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JdbcUtil {
    private Connection connection;

    public JdbcUtil(Connection connection) {
        this.connection = connection;
    }

    public void execute(String query, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            stmt.executeUpdate();
        }
    }

    public void execute(String query, Consumer<PreparedStatement> consumer) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            consumer.accept(stmt);
            stmt.executeUpdate();
        }
    }

    public <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.next()) {
                        throw new SQLException("More than one result found");
                    }
                    rs.first();
                    return mapper.apply(rs);
                } else {
                    return null;
                }
            }
        }
    }

    public <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(mapper.apply(rs));
                }
                return results;
            }
        }
    }
}