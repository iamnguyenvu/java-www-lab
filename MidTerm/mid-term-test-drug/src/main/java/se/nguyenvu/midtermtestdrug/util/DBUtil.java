package se.nguyenvu.midtermtestdrug.util;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {
    private DataSource dataSource;
    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
