package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerCustomerId(Long customerId);
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
    List<Order> findByOrderDateGreaterThan(Date date);
    List<Order> findByOrderDateLessThan(Date date);
    List<Order> findByCustomerCustomerIdAndOrderDateBetween(Long customerId, Date startDate, Date endDate);
    List<Order> findByStatus(String status);
    List<Order> findByCustomerCustomerIdAndStatus(Long customerId, String status);
    List<Order> findByCustomerCustomerIdOrderByOrderDateDesc(Long customerId);
}
