package com.nguyenvu.thymeleafjpashopping.service;

import com.nguyenvu.thymeleafjpashopping.dto.OrderDTO;
import com.nguyenvu.thymeleafjpashopping.dto.OrderLineDTO;
import com.nguyenvu.thymeleafjpashopping.model.Customer;
import com.nguyenvu.thymeleafjpashopping.model.Order;
import com.nguyenvu.thymeleafjpashopping.model.OrderLine;
import com.nguyenvu.thymeleafjpashopping.model.Product;
import com.nguyenvu.thymeleafjpashopping.repository.CustomerRepository;
import com.nguyenvu.thymeleafjpashopping.repository.OrderLineRepository;
import com.nguyenvu.thymeleafjpashopping.repository.OrderRepository;
import com.nguyenvu.thymeleafjpashopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return convertToDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerCustomerIdOrderByOrderDateDesc(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByCustomerIdAndStatus(Long customerId, String status) {
        return orderRepository.findByCustomerCustomerIdAndStatus(customerId, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + orderDTO.getCustomerId()));
        
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPhone(orderDTO.getPhone());
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.ZERO);
        
        Order savedOrder = orderRepository.save(order);
        
        // Add order lines and calculate total
        BigDecimal total = BigDecimal.ZERO;
        if (orderDTO.getOrderLines() != null && !orderDTO.getOrderLines().isEmpty()) {
            for (OrderLineDTO lineDTO : orderDTO.getOrderLines()) {
                Product product = productRepository.findById(lineDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + lineDTO.getProductId()));
                
                // Check stock availability
                if (product.getStock() < lineDTO.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                
                OrderLine orderLine = new OrderLine();
                orderLine.setQuantity(lineDTO.getQuantity());
                orderLine.setUnitPrice(product.getPrice());
                orderLine.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(lineDTO.getQuantity())));
                orderLine.setProduct(product);
                orderLine.setOrder(savedOrder);
                
                orderLineRepository.save(orderLine);
                
                // Update product stock
                productService.updateStock(product.getProductId(), lineDTO.getQuantity());
                
                total = total.add(orderLine.getSubtotal());
            }
        }
        
        savedOrder.setTotalAmount(total);
        Order updatedOrder = orderRepository.save(savedOrder);
        
        return convertToDTO(updatedOrder);
    }

    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        // Validate status transition
        validateStatusTransition(order.getStatus(), newStatus);
        
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        
        return convertToDTO(updatedOrder);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        if (!order.getStatus().equals("PENDING") && !order.getStatus().equals("CONFIRMED")) {
            throw new RuntimeException("Cannot cancel order in status: " + order.getStatus());
        }
        
        // Restore product stock
        for (OrderLine orderLine : order.getOrderLines()) {
            Product product = orderLine.getProduct();
            product.setStock(product.getStock() + orderLine.getQuantity());
            product.setInStock(true);
            productRepository.save(product);
        }
        
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderRepository.delete(order);
    }

    private void validateStatusTransition(String currentStatus, String newStatus) {
        // Define valid transitions
        if (currentStatus.equals("PENDING") && !List.of("CONFIRMED", "CANCELLED").contains(newStatus)) {
            throw new RuntimeException("Invalid status transition from PENDING to " + newStatus);
        }
        if (currentStatus.equals("CONFIRMED") && !List.of("SHIPPING", "CANCELLED").contains(newStatus)) {
            throw new RuntimeException("Invalid status transition from CONFIRMED to " + newStatus);
        }
        if (currentStatus.equals("SHIPPING") && !newStatus.equals("DELIVERED")) {
            throw new RuntimeException("Invalid status transition from SHIPPING to " + newStatus);
        }
        if (currentStatus.equals("DELIVERED") || currentStatus.equals("CANCELLED")) {
            throw new RuntimeException("Cannot change status from " + currentStatus);
        }
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .phone(order.getPhone())
                .customerId(order.getCustomer().getCustomerId())
                .customerName(order.getCustomer().getName())
                .customerUsername(order.getCustomer().getUsername())
                .customerSince(order.getCustomer().getCustomerSince())
                .orderLineCount(order.getOrderLines() != null ? order.getOrderLines().size() : 0)
                .build();
        
        if (order.getOrderLines() != null) {
            List<OrderLineDTO> orderLineDTOs = order.getOrderLines().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setOrderLines(orderLineDTOs);
        }
        
        return dto;
    }

    private OrderLineDTO convertToDTO(OrderLine orderLine) {
        return OrderLineDTO.builder()
                .orderLineId(orderLine.getOrderLineId())
                .quantity(orderLine.getQuantity())
                .unitPrice(orderLine.getUnitPrice())
                .subtotal(orderLine.getSubtotal())
                .orderId(orderLine.getOrder().getOrderId())
                .orderDate(orderLine.getOrder().getOrderDate())
                .orderStatus(orderLine.getOrder().getStatus())
                .productId(orderLine.getProduct().getProductId())
                .productName(orderLine.getProduct().getName())
                .productImageUrl(orderLine.getProduct().getImageUrl())
                .build();
    }
}
