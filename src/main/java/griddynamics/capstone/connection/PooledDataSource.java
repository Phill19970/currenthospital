package griddynamics.capstone.connection;

import com.zaxxer.hikari.*;
import java.sql.SQLException;
import java.sql.Connection;

public class PooledDataSource {
    private HikariDataSource ds;

    public PooledDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5437/mydb");
        config.setUsername("user");
        config.setPassword("password");
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

