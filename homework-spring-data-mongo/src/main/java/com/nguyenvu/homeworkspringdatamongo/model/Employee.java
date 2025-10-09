package com.nguyenvu.homeworkspringdatamongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    private String id;
    
    @Indexed
    private String empId;
    
    @Indexed
    private String empName;
    
    private String email;
    
    @Indexed
    private Integer age;
    
    @Indexed
    private Integer status;
    
    @Indexed
    private String deptId;
    
    @Indexed
    private Integer salary;
}
