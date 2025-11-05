package com.github.imnguyenvu.finalpracticaltest1.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String code;

    @Column(unique = true, length = 100, nullable = false)
    private String name;

    private String description;

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
