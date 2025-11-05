package com.github.imnguyenvu.finalpracticaltest1.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {
    private Long id;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2-100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 150)
    private String email;

    @NotNull(message = "Lương không được để trống")
    @DecimalMin(value = "0.01", message = "Lương phải lớn hơn 0")
    @Digits(integer = 10, fraction = 2, message = "Lương không hợp lệ")
    private BigDecimal salary;

    @NotNull(message = "Ngày vào làm không được để trống")
    @PastOrPresent(message = "Ngày vào làm không được sau hôm nay")
    private LocalDate hireDate;

    @NotNull(message = "Phòng ban không được để trống")
    private Long departmentId;

    private Boolean active;
}
