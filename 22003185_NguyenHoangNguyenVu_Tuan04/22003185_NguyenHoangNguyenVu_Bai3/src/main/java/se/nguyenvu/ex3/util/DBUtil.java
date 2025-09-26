package se.nguyenvu.ex3.util;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {
    private static DataSource dataSource;

    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            System.out.println("Attempting to get database connection...");
            if (dataSource == null) {
                System.err.println("DataSource is null! Check resource injection.");
                throw new RuntimeException("DataSource is null");
            }
            conn = dataSource.getConnection();
            System.out.println("Database connection successful: " + (conn != null));
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection is valid and open");
            }
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get database connection", e);
        }
        return conn;
    }
}
