package com.example.demo.controller;

import com.example.demo.dto.TerminalDto;
import com.example.demo.model.Terminal;
import com.example.demo.service.TerminalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<TerminalDto>> getAllTerminals() {
        return ResponseEntity.ok(terminalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalDto> getTerminalById(@PathVariable("id") Long id) {
        return terminalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveTerminal(@RequestBody @Valid TerminalDto dto) {
        return ResponseEntity.ok(terminalService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalDto> updateTerminal(@PathVariable("id") Long id, @RequestBody @Valid TerminalDto dto) {
        return terminalService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/createTableTerminal")
    public ResponseEntity<String> createTableTerminal() {
        return terminalService.createTable()
                ? ResponseEntity.ok("Successfully created table Terminal")
                : ResponseEntity.badRequest().body("Failed to create table Terminal");
    }

    @PostMapping("/fillTableTerminal")
    public ResponseEntity<String> fillTableTerminal() {
        return terminalService.initializeTable()
                ? ResponseEntity.ok("Filled table Terminal")
                : ResponseEntity.badRequest().body("Failed to fill table Terminal");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTerminalById(@PathVariable("id") Long id) {
        return terminalService.delete(id)
                ? ResponseEntity.ok("Deleted Terminal with id " + id)
                : ResponseEntity.badRequest().body("Failed to delete Terminal with id " + id);
    }

    @DeleteMapping("/clearTableTerminal")
    public ResponseEntity<String> clearTableTerminal() {
        return terminalService.deleteAll()
                ? ResponseEntity.ok("Cleared table Terminal")
                : ResponseEntity.badRequest().body("Failed to clear table Terminal");
    }

    @DeleteMapping("/dropTableTerminal")
    public ResponseEntity<String> dropTableTerminal() {
        return terminalService.dropTable()
                ? ResponseEntity.ok("Dropped table Terminal")
                : ResponseEntity.badRequest().body("Failed to drop table Terminal");
    }
}
