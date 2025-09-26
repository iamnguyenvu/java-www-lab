package se.nguyenvu.ex3.dao;

import se.nguyenvu.ex3.beans.Product;
import se.nguyenvu.ex3.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final DBUtil dbUtil;

    public ProductDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        System.out.println("Executing query: " + query);

        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            int count = 0;
            while (resultSet.next()) {
                count++;
                Product product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .model(resultSet.getString("model"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .description(resultSet.getString("description"))
                        .imgUrl(resultSet.getString("imgUrl"))
                        .build();

                System.out.println("Product " + count + ": " + product.getModel() + " - $" + product.getPrice());
                products.add(product);
            }
            System.out.println("Total products loaded: " + products.size());

        } catch (SQLException e) {
            System.err.println("SQL Exception in findAll(): " + e.getMessage());
            throw new RuntimeException(e);
        }

        return products;
    }

    public Product findById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Product.builder()
                            .id(resultSet.getInt("id"))
                            .model(resultSet.getString("model"))
                            .price(resultSet.getDouble("price"))
                            .quantity(resultSet.getInt("quantity"))
                            .description(resultSet.getString("description"))
                            .imgUrl(resultSet.getString("imgUrl"))
                            .build();
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
