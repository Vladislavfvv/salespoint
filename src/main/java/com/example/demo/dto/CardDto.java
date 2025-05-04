package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDto {
    private Long id;
    private String cardNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String holderName;
    private Long paymentSystemId;
}

