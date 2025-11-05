package com.github.imnguyenvu.finalpracticaltest1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentForm {
    private Long id;

    @NotBlank(message = "Mã phòng ban không được để trống")
    @Pattern(regexp = "^[A-Z0-9]{2,10}$", message = "Mã phòng ban phải viết hoa, 2-10 ký tự chữ/số")
    private String code;

    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(min = 2, max = 100, message = "Tên phòng ban phải từ 2-100 ký tự")
    private String name;

    @Size(max = 255, message = "Mô tả không quá 255 ký tự")
    private String description;
}
