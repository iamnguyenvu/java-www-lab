package se.nguyenvu.ex4.dao;

import se.nguyenvu.ex4.model.Book;
import se.nguyenvu.ex4.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private DBUtil dbUtil;

    public BookDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "select * from books";

        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                books.add(Book.builder()
                        .bookId(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .author(resultSet.getString("author"))
                        .url(resultSet.getString("url"))
                        .price(resultSet.getBigDecimal("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public Book findById(int id) {
        String query = "select * from books where id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Book.builder()
                            .bookId(resultSet.getInt("id"))
                            .title(resultSet.getString("title"))
                            .author(resultSet.getString("author"))
                            .url(resultSet.getString("url"))
                            .price(resultSet.getBigDecimal("price"))
                            .quantity(resultSet.getInt("quantity"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
