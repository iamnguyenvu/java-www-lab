package se.nguyenvu.nguyenhoangnguyenvu_bai5.dao;

import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Department;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private DBUtil dbUtil;

    public DepartmentDAO(DataSource dataSource) {
        dbUtil = new DBUtil(dataSource);
    }

    public List<Department> findAll() {
        List<Department> departments = new ArrayList<Department>();
        String sql = "select * from departments";

        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                departments.add(Department.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public Department findById(int id) throws SQLException {
        String sql = "select * from departments where id = ?";
        try (Connection connection = dbUtil.getConnection();
             var preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Department.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .build();
                }
            }
        }

        return null;
    }

    public Department findDepartmentByEmployeeId(int employeeId) throws SQLException {
        String sql = "select * from departments d join employees e where d.id = e.department_id and e.id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Department.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .build();
                }
            }
        }

        return null;
    }

    public void save(Department department) throws SQLException {
        String sql = "insert into departments (name) values (?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Department department) throws SQLException {
        String sql = "update departments set name = ? where id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from departments where id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
