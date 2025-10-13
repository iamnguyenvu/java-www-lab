package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDTO {
    private Integer orderLineId;
    private Integer amount;
    private BigDecimal purchasePrice;
    private Integer orderId;
    private Calendar orderDate;
    private Integer productId;
    private String productName;
}
