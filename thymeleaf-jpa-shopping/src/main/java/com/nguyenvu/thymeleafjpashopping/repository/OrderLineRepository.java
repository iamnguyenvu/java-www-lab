package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByOrderOrderId(Long orderId);
    List<OrderLine> findByProductProductId(Long productId);
    void deleteByOrderOrderId(Long orderId);
}
