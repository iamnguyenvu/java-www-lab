package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Integer orderId;
    private Calendar orderDate;
    private Integer customerId;
    private String customerName;
    private Calendar customerSince;
    private Integer orderLineCount;
    private BigDecimal totalAmount;
    private Set<OrderLineDTO> orderLines;
}
