package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TerminalDto {
    private Long id;
    @NotBlank(message = "terminalId is required")
    @Size(min = 9, max = 9, message = "terminalId must be exactly 9 characters")
    private String terminalId;
    private Long merchantCategoryCodeId;
    private Long salesPointId;
}
