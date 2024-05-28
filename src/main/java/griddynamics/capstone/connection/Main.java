package griddynamics.capstone.connection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        PooledDataSource dataSource = new PooledDataSource();
        List<Thread> threads = new ArrayList<>();
        
        // Records the start time
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try (Connection connection = dataSource.getConnection();
                     Statement statement = connection.createStatement()) {
                    statement.execute("SELECT pg_sleep(1)");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Recorda the end time and print the execution time
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }
}