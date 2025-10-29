package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long commentId;
    private String text;
    private Date commentDate;
    private Integer rating;
    
    // Product info
    private Long productId;
    private String productName;
    
    // Customer info
    private Long customerId;
    private String customerName;
    private String customerUsername;
}
