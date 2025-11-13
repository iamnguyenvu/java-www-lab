package com.nguyenvu.thymeleafjpashopping.dto;

import jakarta.validation.groups.Default;

/**
 * Validation group for customer registration
 * Extends {@link Default} so standard constraints run alongside registration-only checks.
 */
public interface RegistrationValidation extends Default {
}
