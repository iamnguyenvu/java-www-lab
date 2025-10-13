package com.nguyenvu.thymeleafjpashopping.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Table(name = "customers")
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "customer_since")
    private Calendar customerSince;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Set<Order> orders = new HashSet<>();
}
