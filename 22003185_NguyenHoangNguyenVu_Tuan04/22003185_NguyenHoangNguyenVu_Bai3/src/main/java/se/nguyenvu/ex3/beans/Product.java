package se.nguyenvu.ex3.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String model;
    private Double price;
    private Integer quantity;
    private String description;
    private String imgUrl;
}
