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

    public boolean updateQuantity(int id, int quantity) {
        String query = "update books set quantity = quantity - ? where id = ? and quantity >= ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, quantity);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findBooksByPriceRange(int minPrice, int maxPrice) {
        List<Book> books = new ArrayList<>();
        String query = "select * from books b where b.price between ? and ?";

        try (Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    books.add(Book.builder()
                            .bookId(resultSet.getInt("id"))
                            .title(resultSet.getString("title"))
                            .author(resultSet.getString("author"))
                            .url(resultSet.getString("url"))
                            .price(resultSet.getBigDecimal("price"))
                            .quantity(resultSet.getInt("quantity"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where title like ? or author like ?";
        
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            String searchPattern = "%" + query + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return books;
    }

    public List<Book> searchBooksWithPriceFilter(String query, double minPrice, double maxPrice) {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where (title like ? or author like ?) and price between ? and ?";
        
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            String searchPattern = "%" + query + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setDouble(3, minPrice);
            preparedStatement.setDouble(4, maxPrice);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return books;
    }

    public List<Book> findBooksByMinPrice(double minPrice) {
        List<Book> books = new ArrayList<>();
        String query = "select * from books where price >= ?";

        try (Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, minPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    books.add(Book.builder()
                            .bookId(resultSet.getInt("id"))
                            .title(resultSet.getString("title"))
                            .author(resultSet.getString("author"))
                            .url(resultSet.getString("url"))
                            .price(resultSet.getBigDecimal("price"))
                            .quantity(resultSet.getInt("quantity"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<Book> findBooksByMaxPrice(double maxPrice) {
        List<Book> books = new ArrayList<>();
        String query = "select * from books where price <= ?";

        try (Connection connection = dbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, maxPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    books.add(Book.builder()
                            .bookId(resultSet.getInt("id"))
                            .title(resultSet.getString("title"))
                            .author(resultSet.getString("author"))
                            .url(resultSet.getString("url"))
                            .price(resultSet.getBigDecimal("price"))
                            .quantity(resultSet.getInt("quantity"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
    
    public List<Book> searchBooksWithMinPrice(String query, double minPrice) {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where (title like ? or author like ?) and price <= ?;";
        
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            String searchPattern = "%" + query + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setDouble(3, minPrice);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return books;
    }

    public List<Book> searchBooksWithMaxPrice(String query, double maxPrice) {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where (title like ? or author like ?) and price <= ?";
        
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            String searchPattern = "%" + query + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setDouble(3, maxPrice);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return books;
    }
}
