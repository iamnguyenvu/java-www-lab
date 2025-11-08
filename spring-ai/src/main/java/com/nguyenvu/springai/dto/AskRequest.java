package com.nguyenvu.springai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AskRequest {
    @NotBlank
    private String question;
}
