package com.nguyenvu.homeworkspringdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long deptId;
    private String deptName;
    private Integer empCount;
    private List<EmployeeDTO> employees;
}
