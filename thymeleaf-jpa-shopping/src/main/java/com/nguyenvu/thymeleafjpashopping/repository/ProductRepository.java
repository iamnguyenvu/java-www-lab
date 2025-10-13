package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(BigDecimal low, BigDecimal high);
    List<Product> findByPriceGreaterThan(BigDecimal low);
    List<Product> findByPriceLessThan(BigDecimal low);
    List<Product> findByNameContainingIgnoreCase(String name);
}
