package com.edme.salespoint.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "terminal", schema = "salespointschema")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "terminal_id", length = 9, nullable = false)
    private String terminalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mcc_id", referencedColumnName = "id", nullable = false)
    private MerchantCategoryCode mcc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_point_id", referencedColumnName = "id", nullable = false)
    private SalesPoint salesPoint;
}
