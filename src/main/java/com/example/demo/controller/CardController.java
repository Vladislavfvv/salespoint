package com.example.demo.controller;

import com.example.demo.dto.CardDto;
import com.example.demo.service.CardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDto>> getCards() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable("id") Long id) {
        return cardService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveCard(@RequestBody @Valid CardDto dto) {
        return ResponseEntity.ok(cardService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable("id") Long id, @RequestBody CardDto dto) {
        return cardService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableCard")
    public ResponseEntity<String> createTableCard() {
        log.info("Creating table card");
        if (cardService.createTable()) {
            return ResponseEntity.ok("Table Card created");
        } else {
            return ResponseEntity.badRequest().body("Failed to create table Card");
        }
    }

    @PostMapping("/fillTableCard")
    public ResponseEntity<String> fillTableCard() {
        log.info("Filling table card");
        createTableCard();
        if (cardService.initializeTable()) {
            return ResponseEntity.ok("Table Card filled");
        } else {
            return ResponseEntity.badRequest().body("Failed to fill table Card");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        return cardService.delete(id)
                ? ResponseEntity.ok("Card deleted")
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/clearTableCard")
    public ResponseEntity<String> clearTableCard() {
        log.info("Clearing table card");
        return cardService.deleteAll()
                ? ResponseEntity.ok("Table Card cleared")
                : ResponseEntity.status(500).body("Failed to clear table Card");
    }

    @DeleteMapping("/dropTableCard")
    public ResponseEntity<String> dropTableCard() {
        log.info("Dropping table card");
        return cardService.dropTable()
                ? ResponseEntity.ok("Table Card dropped")
                : ResponseEntity.status(500).body("Failed to drop table Card");
    }
}
