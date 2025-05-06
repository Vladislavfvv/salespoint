package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "acquiring_bank")
public class AcquiringBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bic", length = 9, unique = true, nullable = false)
    private String bic;
    @Column(name = "abbreviated_name", length = 255, nullable = false)
    private String abbreviatedName;
}
