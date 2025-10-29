package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long orderId;
    private Date orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private String phone;
    
    // Customer info
    private Long customerId;
    private String customerName;
    private String customerUsername;
    private Date customerSince;
    
    // OrderLine info
    private Integer orderLineCount;
    private List<OrderLineDTO> orderLines;
}
