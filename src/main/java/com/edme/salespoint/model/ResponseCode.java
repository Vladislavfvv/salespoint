package com.edme.salespoint.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "response_code", schema = "salespointschema")
public class ResponseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "error_code", length = 2, nullable = false)
    private String errorCode;// Код ошибки (например, "00" для успешной  операции).
    @Column(name = "error_description", length = 255, nullable = false)
    private String errorDescription; // Описание ошибки.
    @Column(name = "error_level", length = 255, nullable = false)
    private String errorLevel; //Уровень ошибки (например, "Критическая", "Некритическая").
}
