package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.Category;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(BigDecimal low, BigDecimal high);
    List<Product> findByPriceGreaterThan(BigDecimal low);
    List<Product> findByPriceLessThan(BigDecimal low);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryCategoryId(Long categoryId);
    List<Product> findByInStockTrue();
}
