package com.nguyenvu.thymeleafjpashopping.repository;

import com.nguyenvu.thymeleafjpashopping.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer customerId);
    List<Order> findByDateBetween(Calendar date, Calendar date2);
    List<Order> findByDateGreaterThan(Calendar date);
    List<Order> findByDateLessThan(Calendar date);
    List<Order> findByCustomerIdAndDateBetween(Integer customerId, Calendar date, Calendar date2);
}
