package se.nguyenvu.nguyenhoangnguyenvu_bai5.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employees {
    private int id;
    private String name;
    private Double salary;
    private int departmentId;
}
