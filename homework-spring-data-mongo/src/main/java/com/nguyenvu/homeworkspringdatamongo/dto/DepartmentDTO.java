package com.nguyenvu.homeworkspringdatamongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private String deptId;
    private String deptName;
    private Integer employeeCount;
}
