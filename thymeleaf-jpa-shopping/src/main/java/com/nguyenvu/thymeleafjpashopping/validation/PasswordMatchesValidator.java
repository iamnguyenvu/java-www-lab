package com.nguyenvu.thymeleafjpashopping.validation;

import com.nguyenvu.thymeleafjpashopping.dto.CustomerDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, CustomerDTO> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(CustomerDTO customerDTO, ConstraintValidatorContext context) {
        if (customerDTO == null) {
            return true;
        }
        
        String password = customerDTO.getNewPassword();
        String confirmPassword = customerDTO.getConfirmPassword();
        
        // If both are null or empty, it's valid (other validators will catch required fields)
        if ((password == null || password.isEmpty()) && 
            (confirmPassword == null || confirmPassword.isEmpty())) {
            return true;
        }
        
        // Check if passwords match
        boolean isValid = password != null && password.equals(confirmPassword);
        
        if (!isValid) {
            // Add custom error message to confirmPassword field
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        
        return isValid;
    }
}
