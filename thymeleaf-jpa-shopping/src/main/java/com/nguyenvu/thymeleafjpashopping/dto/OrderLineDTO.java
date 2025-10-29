package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDTO {
    private Long orderLineId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    
    // Order info
    private Long orderId;
    private Date orderDate;
    private String orderStatus;
    
    // Product info
    private Long productId;
    private String productName;
    private String productImageUrl;
}
