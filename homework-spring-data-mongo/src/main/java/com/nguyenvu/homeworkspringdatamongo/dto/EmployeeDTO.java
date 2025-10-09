package com.nguyenvu.homeworkspringdatamongo.dto;

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
    private String empId;
    private String empName;
    private String email;
    private Integer age;
    private Integer salary;
    private String deptId;
    private String deptName;
    private Integer status;
}
