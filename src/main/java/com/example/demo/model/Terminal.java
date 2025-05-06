package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "terminal_id", length = 9, nullable = false)
    private String terminalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mcc", referencedColumnName = "id", nullable = false)
    private MerchantCategoryCode mcc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesPoint", referencedColumnName = "id", nullable = false)
    private SalesPoint salesPoint;
}
