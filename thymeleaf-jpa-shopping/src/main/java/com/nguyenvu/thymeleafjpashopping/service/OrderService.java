package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.dto.OrderLineDTO;
import com.nguyenvu.thymeleafjpashopping.model.Order;
import com.nguyenvu.thymeleafjpashopping.model.OrderLine;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import com.nguyenvu.thymeleafjpashopping.repository.OrderRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .date(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : Calendar.getInstance())
                .customer(customerRepository.getReferenceById(orderDTO.getCustomerId()))
                .build();

        if(orderDTO.getOrderLines() != null) {
            orderDTO.getOrderLines().stream()
                    .filter(Objects::nonNull)
                    .filter(ol -> ol.getProductId() != null && ol.getAmount() != null && ol.getPurchasePrice() != null)
                    .map(olDto -> convertToEntity(olDto, order))
                    .forEach(order::addOrderLine);
        }

        return convertToDTO(orderRepository.save(order));
    }

    public List<OrderDTO> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByDateRange(Calendar start, Calendar end) {
        return orderRepository.findByDateBetween(start, end).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByDateGreaterThan(Calendar date) {
        return orderRepository.findByDateGreaterThan(date).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByDateLessThan(Calendar date) {
        return orderRepository.findByDateLessThan(date).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByCustomerIdAndDateBetween(Integer customerId, Calendar start, Calendar end) {
        return orderRepository.findByCustomerIdAndDateBetween(customerId, start, end).stream()
                .map(this::convertToDTO)
                .toList();
    }

//    Convert DTO helper
    private OrderDTO convertToDTO(Order order) {
        Set<OrderLineDTO> orderLineDTOs = order.getOrderLines() != null ? 
                order.getOrderLines().stream()
                        .map(this::convertToDTO).collect(Collectors.toSet()) : null;
        
        BigDecimal totalAmount = orderLineDTOs != null ? 
                orderLineDTOs.stream()
                        .map(ol -> ol.getPurchasePrice().multiply(BigDecimal.valueOf(ol.getAmount())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
        
        return OrderDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getDate())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .customerSince(order.getCustomer().getCustomerSince())
                .orderLineCount(order.getOrderLines() != null ? order.getOrderLines().size() : 0)
                .totalAmount(totalAmount)
                .orderLines(orderLineDTOs)
                .build();
    }

    private OrderLine convertToEntity(OrderLineDTO orderLineDTO, Order order) {
        OrderLine orderLine = OrderLine.builder()
                .amount(orderLineDTO.getAmount())
                .purchasePrice(orderLineDTO.getPurchasePrice())
                .build();

        if(orderLineDTO.getProductId() != null) {
            orderLine.setProduct(productRepository.getReferenceById(orderLineDTO.getProductId()));
        } else orderLine.setProduct(null);

        orderLine.setOrder(order);
        return orderLine;
    }

    private OrderLineDTO convertToDTO(OrderLine orderLine) {
        return OrderLineDTO.builder()
                .orderLineId(orderLine.getId())
                .orderId(orderLine.getOrder().getId())
                .orderDate(orderLine.getOrder().getDate())
                .productId(orderLine.getProduct().getId())
                .productName(orderLine.getProduct().getName())
                .amount(orderLine.getAmount())
                .purchasePrice(orderLine.getPurchasePrice())
                .build();
    }

}
