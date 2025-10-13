package com.nguyenvu.thymeleafdemo.model;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLine {
    private Product product;
    private Integer amount;
    private BigDecimal purchasePrice;
}
