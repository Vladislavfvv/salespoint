package com.edme.salespoint.controller;

import com.edme.commondto.dto.TransactionExchangeDto;
import com.edme.salespoint.dto.TransactionDto;
import com.edme.salespoint.mapper.TransactionExchangeMapper;
import com.edme.salespoint.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;


    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.save(transactionDto));
    }

//    @PostMapping("/payment/confirm")
//    public ResponseEntity<String> confirmTransaction(@RequestBody @Valid TransactionExchangeDto exchangeDto) {
//        String result = transactionService.confirmAndSave(exchangeDto);
//        return ResponseEntity.ok(result);
//    }
@PostMapping("/external")
public ResponseEntity<TransactionExchangeDto> processExternalTransaction(
        @RequestBody @Valid TransactionExchangeDto exchangeDto) {
    log.info("Received external transaction: {}", exchangeDto);

    TransactionExchangeDto processedTransaction = transactionService.processExternalTransaction(exchangeDto);
    return ResponseEntity.ok(processedTransaction);
}


    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.update(id, transactionDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableTransaction")
    public ResponseEntity<String> createTableTransaction() {
        return transactionService.createTable()
                ? ResponseEntity.ok("Successfully created table Transaction")
                : ResponseEntity.badRequest().body("Failed to create table Transaction");
    }

    @PostMapping("/fillTableTransaction")
    public ResponseEntity<String> fillTableTransaction() {
        return transactionService.initializeTable()
                ? ResponseEntity.ok("Filled table Transaction")
                : ResponseEntity.badRequest().body("Failed to fill table Transaction");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionById(@PathVariable Long id) {
        return transactionService.delete(id)
                ? ResponseEntity.ok("Successfully deleted transaction with id:" + id)
                : ResponseEntity.badRequest().body("Failed to delete transaction with id:" + id);
    }

    @DeleteMapping("/clearTableTransaction")
    public ResponseEntity<String> clearTableTransaction() {
        return transactionService.deleteAll()
                ? ResponseEntity.ok("Successfully cleared table Transaction")
                : ResponseEntity.badRequest().body("Failed to clear table Transaction");
    }

    @DeleteMapping("/dropTableTransaction")
    public ResponseEntity<String> dropTableTransaction() {
        return transactionService.dropTable()
                ? ResponseEntity.ok("Successfully dropped table Transaction")
                : ResponseEntity.badRequest().body("Failed to drop table Transaction");
    }


}
