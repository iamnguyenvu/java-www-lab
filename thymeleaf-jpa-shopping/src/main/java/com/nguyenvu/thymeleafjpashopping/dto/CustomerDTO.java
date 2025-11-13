package com.nguyenvu.thymeleafjpashopping.dto;

import com.nguyenvu.thymeleafjpashopping.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches(groups = RegistrationValidation.class)
public class CustomerDTO {
    private Long customerId;
    
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 100, message = "Tên phải từ 2-100 ký tự")
    @Pattern(regexp = "^(?:\\p{Lu}[\\p{L}'-]*)(?:\\s+\\p{Lu}[\\p{L}'-]*)+$",
             message = "Tên phải có ít nhất 2 từ và mỗi từ bắt đầu bằng chữ hoa")
    private String name;
    
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    @Pattern(regexp = "^[A-Za-z]+\\d*$",
             message = "Tên đăng nhập phải bắt đầu bằng chữ cái, số (nếu có) chỉ được đặt ở cuối")
    private String username;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
             message = "Định dạng email không đúng")
    private String email;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "Số điện thoại phải bắt đầu bằng 0 hoặc +84 và có 10-11 chữ số")
    private String phone;
    
    @Size(min = 10, max = 255, message = "Địa chỉ phải từ 10-255 ký tự")
    private String address;
    
    private Date customerSince;
    private String role;
    private Boolean enabled;
    
    // Order info
    private Integer orderCount;
    private List<OrderDTO> orders;
    
    // Comment info
    private Integer commentCount;
    private List<CommentDTO> comments;
    
    // For registration form (password not included in DTO for security)
    @NotBlank(message = "Mật khẩu không được để trống", groups = RegistrationValidation.class)
    @Size(min = 8, max = 100, message = "Mật khẩu phải từ 8-100 ký tự", groups = RegistrationValidation.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Mật khẩu phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt (@$!%*?&)",
             groups = RegistrationValidation.class)
    private String newPassword;
    
    @NotBlank(message = "Xác nhận mật khẩu không được để trống", groups = RegistrationValidation.class)
    private String confirmPassword;
    
    // Alias methods for backward compatibility with templates
    public Long getId() {
        return customerId;
    }
    
    public void setId(Long id) {
        this.customerId = id;
    }
}

