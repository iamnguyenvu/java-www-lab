package com.nguyenvu.homeworkspringdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long empId;
    private String empName;
    private Date dob;
    private BigDecimal salary;
    private Integer age;
    private Long deptId;
    private String deptName;
}
