package com.nguyenvu.thymeleafjpashopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean inStock;
    private Integer commentCount;
    private List<CommentDTO> comments;
    private Integer orderLineCount;
    private Set<OrderLineDTO> orderLines;

}
