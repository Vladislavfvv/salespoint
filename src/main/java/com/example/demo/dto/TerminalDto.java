package com.example.demo.dto;

import lombok.Data;

@Data
public class TerminalDto {
    private Long id;
    private String terminalId;
    private Long merchantCategoryCodeId;
    private Long salesPointId;
}
