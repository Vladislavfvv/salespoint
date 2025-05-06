package com.example.demo.controller;

import com.example.demo.dto.AcquiringBankDto;
import com.example.demo.model.AcquiringBank;
import com.example.demo.repository.AcquiringBankRepository;
import com.example.demo.service.AcquiringBankService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/acquringBanks")
public class AcquiringBankController {

    @Autowired
    private AcquiringBankService acquiringBankService;

    @GetMapping
    public ResponseEntity<List<AcquiringBankDto>> getAllAcquiringBanks() {
        return ResponseEntity.ok(acquiringBankService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquiringBankDto> getAcquiringBankById(@PathVariable("id") Long id) {
        return acquiringBankService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveAcquiringBank(@RequestBody @Valid AcquiringBankDto acquiringBank) {
        Optional<AcquiringBankDto> acquiringBankDtoOptional = acquiringBankService.save(acquiringBank);
        if (acquiringBankDtoOptional.isPresent()) {
            return ResponseEntity.ok(acquiringBankDtoOptional.get());
        } else {
            return ResponseEntity.badRequest().body("AcquiringBank with that parameters is exists");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcquiringBankDto> update(@PathVariable("id") Long id, @RequestBody @Valid AcquiringBankDto acquiringBankDto) {
        return acquiringBankService.update(id, acquiringBankDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AcquiringBankDto> delete(@PathVariable Long id) {
        return acquiringBankService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/createTableAcquiringBank")
    public ResponseEntity<String> createTableAcquiringBank(){
        log.info("Creating table acquiring bank");
        if(acquiringBankService.createTable()) {
            return ResponseEntity.ok("Successfully created table AcquiringBank");
        } else {
            return ResponseEntity.badRequest().body("Failed to create table AcquiringBank");
        }
    }

    @DeleteMapping("/clearTableAcquiringBank")
    public ResponseEntity<String> clearTableAcqiringBank(){
        log.info("Clearing table acquiring bank");
        if(acquiringBankService.deleteAll()){
            return ResponseEntity.ok("Successfully cleared table AcquiringBank");
        } else {
            return ResponseEntity.status(500).body("Failed to clear table AcquiringBank");
        }
    }

    @DeleteMapping("/dropTableAcquiringBank")
    public ResponseEntity<String> dropTableAcquiringBank(){
        log.info("Dropping table acquiring bank");
        if(acquiringBankService.dropTable()){
            return ResponseEntity.ok("Successfully dropped table AcquiringBank");
        } else {
            return ResponseEntity.status(500).body("Failed to dropped table AcquiringBank");
        }
    }

    @PostMapping("/fillTableAcquiringBank")
    public ResponseEntity<String> fillTableAcquiringBank(){
        log.info("Filling table acquiring bank");
        if(acquiringBankService.initializeTable()){
            return ResponseEntity.ok("Successfully filled table AcquiringBank");
        } else {
            return ResponseEntity.status(500).body("Failed to fill table AcquiringBank");
        }
    }
}
