package com.nguyenvu.thymeleafdemo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table()
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean inStock;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
