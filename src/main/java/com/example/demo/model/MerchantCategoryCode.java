package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "merchant_category_code")
public class MerchantCategoryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mcc", length = 4, nullable = false)
    private String mcc;
    @Column(name = "mcc_name", length = 255, nullable = false)
    private String mccName;
}
