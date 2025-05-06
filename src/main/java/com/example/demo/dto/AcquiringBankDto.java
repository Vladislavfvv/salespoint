package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AcquiringBankDto {
    private Long id;
    @NotBlank(message = "bic is required")
    @Size(min = 9, max = 9, message = "bic must be exactly 9 characters")
    private String bic;
    @NotBlank(message = "AcquiringBank name is required")
    @Size(max = 255, message = "AcquiringBank name must be at most 255 characters")
    private String abbreviatedName;
}
