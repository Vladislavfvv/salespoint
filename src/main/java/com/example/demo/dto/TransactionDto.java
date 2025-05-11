package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
//public class TransactionDto {
//    private Long id;
//    private LocalDate transactionDate;
//    private BigDecimal sum;
//    private Long transactionTypeId;
//    private Long cardId;
//    private Long terminalId;
//    private Long responseCodeId;
//    @NotBlank(message = "terminalId is required")
//    @Size(min = 6, max = 6, message = "terminalId must be exactly 6 characters")
//    private String authorizationCode;
//}
public class TransactionDto {
    private Long id;
    private LocalDate transactionDate;
    private BigDecimal sum;
    @NotNull(message = "TransactionType id is required")
    private TransactionTypeDto transactionType;
    @NotNull(message = "Card id is required")
    private CardDto card;
    @NotNull(message = "Terminal id is required")
    private TerminalDto terminal;
    @NotNull(message = "ResponseCode id is required")
    private ResponseCodeDto responseCode;
    @NotBlank(message = "authorizationCode is required")
    @Size(min = 6, max = 6, message = "authorizationCode must be exactly 6 characters")
    private String authorizationCode;
}

