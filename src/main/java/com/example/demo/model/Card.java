package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {  @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @Column(name = "card_number", length = 50, nullable = false)
    private String cardNumber;
    @Column(name = "expiration_date")
    private Date expirationDate;//срок действия карты
    @Column(name = "holder_name", length = 50, nullable = false)
    private String holderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_system", referencedColumnName = "id", nullable = false)
    private PaymentSystem paymentSystem; //ссылка на плат сист

}
