package se.nguyenvu.ex4.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private DataSource dataSource;

    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
        }
        
        Connection connection = dataSource.getConnection();
        return connection;
    }
}
