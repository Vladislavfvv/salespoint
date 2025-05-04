package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private LocalDate transactionDate;
    private BigDecimal sum;
    private Long transactionTypeId;
    private Long cardId;
    private Long terminalId;
    private Long responseCodeId;
    private String authorizationCode;
}
