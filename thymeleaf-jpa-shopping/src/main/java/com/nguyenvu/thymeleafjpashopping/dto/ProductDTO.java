package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean inStock;
    private Integer stock;
    private String imageUrl;
    
    // Category info
    private Long categoryId;
    private String categoryName;
    
    // Comment info
    private Integer commentCount;
    private List<CommentDTO> comments;
    private Double averageRating;
    
    // OrderLine info
    private Integer orderLineCount;
    private List<OrderLineDTO> orderLines;
}
