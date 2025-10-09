package com.nguyenvu.homeworkspringdatajdbc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    private Long deptId;

    private String deptName;

    @MappedCollection(idColumn = "dept_id")
    private Set<Employee> employees = new LinkedHashSet<>();
}
