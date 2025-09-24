package se.nguyenvu.nguyenhoangnguyenvu_bai5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employees {
    private int id;
    private String name;
    private Double salary;
    private int departmentId;
}
