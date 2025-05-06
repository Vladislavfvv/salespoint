package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDto {
    private Long id;
    @NotBlank(message = "cardNumber is required")
    @Size(max = 50, message = "cardNumber must be at most 50 characters")
    private String cardNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    @NotBlank(message = "holderName is required")
    @Size(max = 50, message = "holderName must be at most 50 characters")
    private String holderName;
    @NotNull(message = "paymentSystemId ID is required")
    private Long paymentSystemId;
}

