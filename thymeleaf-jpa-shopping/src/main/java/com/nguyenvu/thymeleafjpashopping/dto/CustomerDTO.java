package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long customerId;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private Calendar customerSince;
    private String role;
    private Boolean enabled;
    
    // Order info
    private Integer orderCount;
    private List<OrderDTO> orders;
    
    // Comment info
    private Integer commentCount;
    private List<CommentDTO> comments;
    
    // For registration form (password not included in DTO for security)
    private String newPassword;
    private String confirmPassword;
}
