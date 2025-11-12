package com.nguyenvu.thymeleafjpashopping.dto;

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
public class CustomerDTO {
    private Long customerId;
    
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 100, message = "Tên phải từ 2-100 ký tự")
    private String name;
    
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập chỉ chứa chữ cái, số và dấu gạch dưới")
    private String username;
    
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String phone;
    
    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
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
    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6-100 ký tự", groups = RegistrationValidation.class)
    private String newPassword;
    
    @NotBlank(message = "Xác nhận mật khẩu không được để trống", groups = RegistrationValidation.class)
    private String confirmPassword;
    
    // Validation group
    public interface RegistrationValidation {}
    
    // Alias methods for backward compatibility with templates
    public Long getId() {
        return customerId;
    }
    
    public void setId(Long id) {
        this.customerId = id;
    }
}

