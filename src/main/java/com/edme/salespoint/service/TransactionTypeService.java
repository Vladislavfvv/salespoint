package com.edme.salespoint.service;

import com.edme.salespoint.dto.TransactionTypeDto;
import com.edme.salespoint.mapper.TransactionTypeMapper;
import com.edme.salespoint.repository.TransactionTypeRepository;
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
public class TransactionTypeService {

    @Autowired
    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private final TransactionTypeMapper transactionTypeMapper;

    public List<TransactionTypeDto> findAll() {
        return transactionTypeRepository.findAll()
                .stream().map(transactionTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionTypeDto> findById(Long id) {
        return transactionTypeRepository.findById(id)
                .map(transactionTypeMapper::toDto);
    }

    @Transactional
    public Optional<TransactionTypeDto> save(TransactionTypeDto dto) {
        Optional<TransactionTypeDto> exiting = findById(dto.getId());
        if (exiting.isPresent()) {
            log.info("TransactionType with id {} already exists", dto.getId());
            return Optional.empty();
        }
        dto.setId(null);
        transactionTypeRepository.saveAndFlush(transactionTypeMapper.toEntity(dto));
        log.info("TransactionType with id {} saved", dto.getId());
        return Optional.of(dto);
    }

    @Transactional
    public Optional<TransactionTypeDto> update(Long id, TransactionTypeDto dto) {
        Optional<TransactionTypeDto> exiting = findById(id);
        if (exiting.isPresent()) {
            TransactionTypeDto exitingDto = exiting.get();
            exitingDto.setTransactionTypeName(dto.getTransactionTypeName());
            exitingDto.setOperator(dto.getOperator());
            transactionTypeRepository.save(transactionTypeMapper.toEntity(exitingDto));
            log.info("TransactionType with id {} updated", dto.getId());
            return Optional.of(exitingDto);
        } else {
            log.info("TransactionType with id {} not found", dto.getId());
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<TransactionTypeDto> exiting = findById(id);
        if (exiting.isPresent()) {
            transactionTypeRepository.deleteById(id);
            log.info("TransactionType with id {} deleted", id);
            return true;
        } else {
            log.info("TransactionType with id {} not found", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try{
            transactionTypeRepository.deleteAll();
            log.info("All transaction types deleted");
            return true;
        } catch (Exception e) {
            log.info("All transaction types not found");
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            transactionTypeRepository.dropTable();
            log.info("Table transactionTypes dropped");
            return true;
        } catch (Exception e) {
            log.info("Table transactionTypes not found");
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            transactionTypeRepository.createTable();
            log.info("Table transactionTypes created");
            return true;
        }catch (Exception e) {
            log.info("Table transactionTypes not found");
            return false;
        }
    }
    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try {
            transactionTypeRepository.insertDefaultValues();
            log.info("Table transactionTypes initialized");
            return true;
        } catch (Exception e) {
            log.info("Error inserting default values into Table transactionTypes");
            return false;
        }
    }
}
