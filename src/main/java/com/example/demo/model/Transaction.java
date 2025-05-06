package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_date", nullable = false )
    private Date transactionDate;
    @Column(name = "sum")
    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type", referencedColumnName = "id", nullable = false)
    private TransactionType transactionType;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card", referencedColumnName = "id", nullable = false)
    private Card card;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal", referencedColumnName = "id", nullable = false)
    private Terminal terminal;  //change!!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_code", referencedColumnName = "id", nullable = false)
    private ResponseCode responseCode;  //change!!!
    @Column(name = "authorization_code", length = 6, nullable = false)
    private String authorizationCode;

}
