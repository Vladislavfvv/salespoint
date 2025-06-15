package com.edme.salespoint.service;

import com.edme.commondto.dto.TransactionExchangeDto;
import com.edme.salespoint.dto.TransactionDto;
import com.edme.salespoint.mapper.TransactionExchangeMapper;
import com.edme.salespoint.mapper.TransactionMapper;
import com.edme.salespoint.model.Transaction;
import com.edme.salespoint.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final Tracer tracer;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionExchangeMapper transactionExchangeMapper;

    public List<TransactionDto> findAll() {
        return transactionRepository.findAll()
                .stream().map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDto> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDto);
    }


//    @Transactional
//    public Optional<TransactionDto> save(TransactionDto dto) {
//        // Если id в DTO задан, проверяем, есть ли такая транзакция в базе
//        if (dto.getId() != null) {
//            Optional<TransactionDto> transaction = findById(dto.getId());
//            if (transaction.isPresent()) {
//                log.info("Transaction {} already exists", dto.getId());
//                return Optional.empty();
//            }
//        }
//
//        // Сбрасываем id, чтобы гарантировать создание новой записи
//        dto.setId(null);
//
//        Transaction entity = transactionMapper.toEntity(dto);
//        transactionRepository.saveAndFlush(entity);
//
//        log.info("Transaction saved with id {}", entity.getId());
//        return Optional.of(transactionMapper.toDto(entity));
//    }


    @Transactional
    public Optional<TransactionDto> save(TransactionDto dto) {
        Span span = tracer.spanBuilder("TransactionService.save").startSpan();
        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("transaction.request.id", String.valueOf(dto.getId()));
            span.setAttribute("transaction.request.sum", dto.getSum() != null ? dto.getSum().toString() : "null");


            // Если id в DTO задан, проверяем, есть ли такая транзакция в базе
            if (dto.getId() != null) {
                Optional<TransactionDto> transaction = findById(dto.getId());
                if (transaction.isPresent()) {
                    log.info("Transaction {} already exists", dto.getId());
                    span.setStatus(io.opentelemetry.api.trace.StatusCode.UNSET, "Transaction already exists");
                    return Optional.empty();
                }
            }

            // Сбрасываем id, чтобы гарантировать создание новой записи
            dto.setId(null);

            Transaction entity = transactionMapper.toEntity(dto);
            transactionRepository.saveAndFlush(entity);

            log.info("Transaction saved with id {}", entity.getId());
            span.setAttribute("transaction.saved.id", String.valueOf(entity.getId()));
            span.setStatus(io.opentelemetry.api.trace.StatusCode.OK, "Transaction saved");

            return Optional.of(transactionMapper.toDto(entity));
        } catch (Exception ex) {
            span.recordException(ex);
            span.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR, "Error saving transaction");
            throw ex;
        } finally {
            span.end();
        }
    }


//    @Transactional
//    public Optional<TransactionDto> save(TransactionDto dto) {
//        Optional<TransactionDto> transaction = findById(dto.getId());
//        if (transaction.isPresent()) {
//            log.info("Transaction {} already exists", dto.getId());
//            return Optional.empty();
//        } else {
//            dto.setId(null);
//
//            Transaction entity = transactionMapper.toEntity(dto);
//            transactionRepository.saveAndFlush(entity);
//
//            log.info("Transaction {} saved", dto.getId());
//            return Optional.of(transactionMapper.toDto(entity));
//        }
//    }

//    public String confirmAndSave(TransactionExchangeDto exchangeDto) {
//        TransactionDto dto = transactionExchangeMapper.toTransactionDto(exchangeDto);
//
//        Optional<TransactionDto> result = save(dto);
//
//        if (result.isPresent()) {
//            return "Transaction saved in sales-point: " + result.get().getId();
//        } else {
//            return "Transaction already exists in sales-point";
//        }
//    }

    @Transactional
    public TransactionExchangeDto processExternalTransaction(TransactionExchangeDto exchangeDto) {
        // Конвертируем TransactionExchangeDto в TransactionDto
        TransactionDto transactionDto = transactionExchangeMapper.toTransactionDto(exchangeDto);
        transactionDto.setId(null); //сброс чтобы сохранить под новым номером

        // Сохраняем транзакцию
        Optional<TransactionDto> savedTransaction = save(transactionDto);

        if (savedTransaction.isPresent()) {
            log.info("Transaction {} saved successfully", savedTransaction.get().getId());
            // Конвертируем обратно в TransactionExchangeDto
            return transactionExchangeMapper.toExchangeDto(savedTransaction.get());
        } else {
            log.warn("Transaction already exists with data: {}", exchangeDto);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Transaction already exists");
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
        if (!createTable()) {
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
