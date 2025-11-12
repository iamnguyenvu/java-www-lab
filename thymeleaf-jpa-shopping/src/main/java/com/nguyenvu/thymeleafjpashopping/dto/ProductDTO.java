package com.nguyenvu.thymeleafjpashopping.dto;

import jakarta.validation.constraints.*;
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
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 200, message = "Tên sản phẩm phải từ 2-200 ký tự")
    private String name;
    
    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String description;
    
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Digits(integer = 10, fraction = 2, message = "Giá không hợp lệ")
    private BigDecimal price;
    
    private Boolean inStock;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer stock;
    
    @Size(max = 500, message = "URL hình ảnh tối đa 500 ký tự")
    private String imageUrl;
    
    // Category info
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    private String categoryName;
    
    // Comment info
    private Integer commentCount;
    private List<CommentDTO> comments;
    private Double averageRating;
    
    // OrderLine info
    private Integer orderLineCount;
    private List<OrderLineDTO> orderLines;
    
    // Alias for compatibility
    public String getProductName() {
        return name;
    }
    
    public void setProductName(String productName) {
        this.name = productName;
    }
}
