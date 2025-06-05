package com.edme.salespoint.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "transaction_type", schema = "salespointschema")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_type_name", length = 255, nullable = false)
    private String transactionTypeName;
    @Column(name = "operator", length = 1, nullable = false)
    private String operator;
}
