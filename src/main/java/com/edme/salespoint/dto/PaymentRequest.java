package com.edme.salespoint.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String cardNumber;
    private BigDecimal amount;
}
