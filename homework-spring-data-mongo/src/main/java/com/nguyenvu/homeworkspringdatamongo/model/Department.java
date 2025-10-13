package com.nguyenvu.homeworkspringdatamongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    private String id;
    
    @Indexed
    private String deptId;
    
    @Indexed
    @Field("deptName")
    private String deptName;
    
    @Field("name")
    private String name;
    
    private List<EmbeddedEmployee> employees;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EmbeddedEmployee {
        private String empId;
        private String empName;
        private String email;
        private Integer age;
        private Integer status;
        private Integer salary;
    }
}
