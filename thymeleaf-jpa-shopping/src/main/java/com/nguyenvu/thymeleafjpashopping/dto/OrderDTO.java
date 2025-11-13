package com.nguyenvu.thymeleafjpashopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    @Size(min = 10, max = 255, message = "Địa chỉ giao hàng phải từ 10-255 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s.,/\\-()]+$", message = "Địa chỉ chứa ký tự không hợp lệ")
    private String shippingAddress;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "Số điện thoại phải bắt đầu bằng 0 hoặc +84 và có 10-11 chữ số")
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
