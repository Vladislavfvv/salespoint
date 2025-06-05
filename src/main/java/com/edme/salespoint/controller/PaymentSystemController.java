package com.edme.salespoint.controller;

import com.edme.salespoint.dto.PaymentSystemDto;
import com.edme.salespoint.service.PaymentSystemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/paymentSystems")
public class PaymentSystemController {

    @Autowired
    private PaymentSystemService paymentSystemService;

    @GetMapping
    public ResponseEntity<List<PaymentSystemDto>> getAllPaymentSystems() {
        return ResponseEntity.ok(paymentSystemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSystemDto> getPaymentSystemById(@PathVariable("id") Long id) {
        return paymentSystemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<?> savePaymentSystem(@RequestBody @Valid PaymentSystemDto dto) {
        return ResponseEntity.ok(paymentSystemService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystemDto> updatePaymentSystem(@PathVariable("id") Long id, @RequestBody @Valid PaymentSystemDto dto) {
        return paymentSystemService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTablePaymentSystem")
    public ResponseEntity<String> createTablePaymentSystem() {
        log.info("Creating table payment system");
        if (paymentSystemService.createTable()) {
            return ResponseEntity.ok("Table PaymentSystem created");
        }
        return ResponseEntity.badRequest().body("Failed to create table PaymentSystem");
    }

    @PostMapping("/fillTablePaymentSystem")
    public ResponseEntity<String> fillTablePaymentSystem() {
        log.info("Filling table payment system");
        createTablePaymentSystem();
        if (paymentSystemService.initializeTable()) {
            return ResponseEntity.ok("Table PaymentSystem filled");
        } else {
            return ResponseEntity.status(500).body("Failed to fill table PaymentSystem");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentSystemDto> deletePaymentSystem(@PathVariable("id") Long id) {
        Optional<PaymentSystemDto> deleted = paymentSystemService.findById(id);
        return paymentSystemService.delete(id)
                ? ResponseEntity.ok(deleted.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clearTablePaymentSystem")
    public ResponseEntity<String> clearTablePaymentSystem() {
        log.info("Clearing table payment system");
        return paymentSystemService.deleteAll()
                ? ResponseEntity.ok("Table payment system cleared")
                : ResponseEntity.status(500).body("Failed to clear table PaymentSystem");
    }

    @DeleteMapping("/dropTablePaymentSystem")
    public ResponseEntity<String> dropTablePaymentSystem() {
        log.info("Dropping table payment system");
        return paymentSystemService.dropTable()
                ? ResponseEntity.ok("Table payment system dropped")
                : ResponseEntity.status(500).body("Failed to drop table PaymentSystem");
    }
}


