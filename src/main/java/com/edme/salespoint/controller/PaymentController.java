package com.edme.salespoint.controller;

import com.edme.salespoint.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody PaymentRequest request) {
        System.out.println("Подтверждение оплаты от Processing Center: " + request);
        return ResponseEntity.ok("Оплата подтверждена");
    }
}
