package com.example.demo.service;

import com.example.demo.dto.PaymentSystemDto;
import com.example.demo.mapper.PaymentSystemMapper;
import com.example.demo.model.PaymentSystem;
import com.example.demo.repository.PaymentSystemRepository;
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
public class PaymentSystemService {
    @Autowired
    private final PaymentSystemRepository paymentSystemRepository;
    @Autowired
    private final PaymentSystemMapper paymentSystemMapper;

    public List<PaymentSystemDto> findAll() {
        return paymentSystemRepository.findAll()
                .stream().map(paymentSystemMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<PaymentSystemDto> findById(Long id) {
        return paymentSystemRepository.findById(id)
                .map(paymentSystemMapper::toDto);
    }

    @Transactional
    public Optional<PaymentSystemDto> save(PaymentSystemDto paymentSystemDto) {
        Optional<PaymentSystem> exciting = paymentSystemRepository.findById(paymentSystemDto.getId());
        if (exciting.isPresent()) {
            log.info("PaymentSystem {} already exists", paymentSystemDto.getId());
            return Optional.empty();
        } else {
            paymentSystemDto.setId(null);
            paymentSystemDto.setPaymentSystemName(paymentSystemDto.getPaymentSystemName());
            paymentSystemRepository.saveAndFlush(paymentSystemMapper.toEntity(paymentSystemDto));
            log.info("Creating PaymentSystem {}", paymentSystemDto.getId());
            return Optional.of(paymentSystemDto);
        }
    }

    @Transactional
    public Optional<PaymentSystemDto> update(Long id, PaymentSystemDto dto) {
        Optional<PaymentSystemDto> exciting = findById(id);
        if (exciting.isPresent()) {
            PaymentSystemDto entityDto = exciting.get();

            entityDto.setId(null);
            entityDto.setPaymentSystemName(dto.getPaymentSystemName());
            paymentSystemRepository.save(paymentSystemMapper.toEntity(entityDto));
            log.info("PaymentSystem {} updated", id);
            return Optional.of(entityDto);
        } else {
            log.info("PaymentSystem {} not found", id);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<PaymentSystemDto> exciting = findById(id);
        if (exciting.isPresent()) {
            paymentSystemRepository.deleteById(id);
            log.info("PaymentSystem {} deleted", id);
            return true;
        } else {
            log.info("PaymentSystem {} not found", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try{
            paymentSystemRepository.deleteAll();
            log.info("All PaymentSystems deleted");
            return true;
        }catch (Exception e){
            log.info("Error deleting all PaymentSystems {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try{
            paymentSystemRepository.dropTable();
            log.info("All PaymentSystems dropped");
            return true;
        }catch (Exception e){
            log.info("Error dropping all PaymentSystems {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            paymentSystemRepository.createTable();
            log.info("All PaymentSystems created");
            return true;
        }catch (Exception e){
            log.info("Error creating all PaymentSystems {}", e.getMessage());
            return false;
        }
    }


    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try{
            paymentSystemRepository.insertDefaultValues();
            log.info("All PaymentSystems initialized");
            return true;
        }catch (Exception e){
            log.info("Error inserting default values into table: PaymentSystems {}", e.getMessage());
            return false;
        }
    }
}
