package se.nguyenvu.nguyenhoangnguyenvu_bai5.dao;

import se.nguyenvu.nguyenhoangnguyenvu_bai5.model.Employees;
import se.nguyenvu.nguyenhoangnguyenvu_bai5.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {
    private DBUtil dbUtil;

    public EmployeesDAO(DataSource dataSource) {
        dbUtil = new DBUtil(dataSource);
    }

    public List<Employees> findAll() throws SQLException {
        List<Employees> employees = new ArrayList<Employees>();
        String sql = "select * from employees";

        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                employees.add(Employees.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .salary(resultSet.getDouble("salary"))
                        .departmentId(resultSet.getInt("department_id"))
                        .build());
            }

            System.out.println(employees.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    public Employees findById(int id) throws SQLException {
        String sql = "select * from employees where id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return Employees.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .salary(resultSet.getDouble("salary"))
                            .departmentId(resultSet.getInt("department_id"))
                            .build();
                }
            }
        }

        return null;
    }

    public List<Employees> findByDepartmentId(int departmentId) throws SQLException {
        List<Employees> employees = new ArrayList<>();
        String sql = "select * from employees where department_id = ?";

        try (Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, departmentId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(Employees.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .salary(resultSet.getDouble("salary"))
                            .departmentId(resultSet.getInt("department_id"))
                            .build());
                }
            }
        }

        return employees;
    }

    public void save(Employees employees) throws SQLException {
        String sql = "insert into employees(id, name, department_id, salary) values(?,?,?,?)";
        try (Connection connection = dbUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employees.getId());
            preparedStatement.setString(2, employees.getName());
            preparedStatement.setInt(3, employees.getDepartmentId());
            preparedStatement.setDouble(4, employees.getSalary());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Employees employees) throws SQLException {
        String sql = "update employees set name = ?, salary = ?, department_id = ? where id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employees.getName());
            preparedStatement.setDouble(2, employees.getSalary());
            preparedStatement.setInt(3, employees.getDepartmentId());
            preparedStatement.setInt(4, employees.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from employees where id = ?";
        try(Connection connection = dbUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
