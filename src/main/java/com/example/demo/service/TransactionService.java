package com.example.demo.service;

import com.example.demo.dto.TransactionDto;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.model.Terminal;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionMapper transactionMapper;

    public List<TransactionDto> findAll() {
        return transactionRepository.findAll()
                .stream().map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDto> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDto);
    }

    @Transactional
    public Optional<TransactionDto> save(TransactionDto dto) {
        Optional<TransactionDto> transaction = findById(dto.getId());
        if (transaction.isPresent()) {
            log.info("Transaction {} already exists", dto.getId());
            return Optional.empty();
        } else {
            dto.setId(null);

            Transaction entity = transactionMapper.toEntity(dto);
            transactionRepository.saveAndFlush(entity);

            log.info("Transaction {} saved", dto.getId());
            return Optional.of(transactionMapper.toDto(entity));
        }
    }

    @Transactional
    public Optional<TransactionDto> update(Long id, TransactionDto dto) {
        Optional<TransactionDto> transaction = findById(id);
        if (transaction.isPresent()) {
            TransactionDto transactionDto = transaction.get();

            transactionDto.setId(dto.getId());
            transactionDto.setTransactionDate(dto.getTransactionDate());
            transactionDto.setSum(dto.getSum());
            transactionDto.setTransactionType(dto.getTransactionType());
            transactionDto.setCard(dto.getCard());
            transactionDto.setTerminal(dto.getTerminal());
            transactionDto.setResponseCode(dto.getResponseCode());
            transactionDto.setAuthorizationCode(dto.getAuthorizationCode());

            transactionRepository.save(transactionMapper.toEntity(transactionDto));
            log.info("Transaction {} updated", dto);
            return Optional.of(transactionDto);
        } else {
            log.info("Transaction {} not found", id);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<TransactionDto> transaction = findById(id);
        if (transaction.isPresent()) {
            transactionRepository.deleteById(id);
            log.info("Transaction {} deleted", id);
            return true;
        } else {
            log.info("Transaction {} not found", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            transactionRepository.deleteAll();
            log.info("Transaction {} deleted", transactionRepository.findAll().size());
            return true;
        } catch (Exception e) {
            log.info("Transaction {} not found", transactionRepository.findAll().size());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            transactionRepository.dropTable();
            log.info("Transaction table dropped");
            return true;
        } catch (Exception e) {
            log.info("Transaction table not found");
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            transactionRepository.createTable();
            log.info("Transaction table created");
            return true;
        } catch (Exception e) {
            log.info("Transaction table not created");
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if(!createTable()) {
            log.info("Error initializing table Transaction  cause table Transaction not created");
            return false;
        }
        try {
            transactionRepository.insertDefaultValues();
            log.info("Transaction table initialized");
            return true;
        } catch (Exception e) {
            log.info("Error initializing table Transaction : {}", e.getMessage());
            return false;
        }
    }
}
