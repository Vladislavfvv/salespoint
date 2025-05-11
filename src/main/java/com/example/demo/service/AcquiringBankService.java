package com.example.demo.service;

import com.example.demo.dto.AcquiringBankDto;
import com.example.demo.mapper.AcquiringBankMapper;
import com.example.demo.model.AcquiringBank;
import com.example.demo.repository.AcquiringBankRepository;
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
public class AcquiringBankService {
    @Autowired
    private final AcquiringBankRepository acquiringBankRepository;
    @Autowired
    private final AcquiringBankMapper acquiringBankMapper;

    public List<AcquiringBankDto> findAll() {
        return acquiringBankRepository.findAll()
                .stream()
                .map(acquiringBankMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AcquiringBankDto> findById(Long id) {
        return acquiringBankRepository.findById(id)
                .map(acquiringBankMapper::toDto);
    }

    @Transactional
    public Optional<AcquiringBankDto> save(AcquiringBankDto dto) {
        Optional<AcquiringBankDto> exiting = findById(dto.getId());
        if (exiting.isPresent()) {
            log.info("Acquiring bank {} already exists", dto.getId());
            return Optional.empty();
        }
        dto.setId(null);
        acquiringBankRepository.saveAndFlush(acquiringBankMapper.toEntity(dto));
        log.info("Acquiring bank {} saved", dto.getId());
        return Optional.of(dto);
    }

    @Transactional
    public Optional<AcquiringBankDto> update(Long id, AcquiringBankDto dto) {
        Optional<AcquiringBankDto> existing = findById(id);
        if (existing.isPresent()) {
            AcquiringBankDto entityDto = existing.get();

            //  entityDto.setId(null);
            entityDto.setBic(dto.getBic());
            entityDto.setAbbreviatedName(dto.getAbbreviatedName());
            acquiringBankRepository.save(acquiringBankMapper.toEntity(entityDto));
            log.info("Acquiring bank {} updated", entityDto.getId());
            return Optional.of(entityDto);
        } else {
            log.info("Acquiring bank {} not found", id);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<AcquiringBankDto> exiting = findById(id);
        if (exiting.isPresent()) {
            acquiringBankRepository.deleteById(id);
            log.info("Acquiring bank {} deleted", id);
            return true;
        } else {
            log.info("Acquiring bank {} not found", id);
        }
        return false;
    }

    @Transactional
    public boolean deleteAll() {
        try {
            acquiringBankRepository.deleteAll();
            log.info("All acquiring banks deleted");
            return true;
        } catch (Exception e) {
            log.error("Error deleting all AcquiringBanks {}", e.getMessage());
        }
        return false;
    }

    @Transactional
    public boolean dropTable() {
        try {
            acquiringBankRepository.dropTable();
            log.info("Table AcquiringBank dropped");
            return true;
        } catch (Exception e) {
            log.error("Error dropping Table AcquiringBanks {}", e.getMessage());
        }
        return false;
    }

    @Transactional
    public boolean createTable() {
        try {
            acquiringBankRepository.createTable();
            log.info("Table AcquiringBank created");
            return true;
        } catch (Exception e) {
            log.error("Error creating Table AcquiringBanks {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try {
            acquiringBankRepository.insertDefaultValues();
            log.info("Default values inserted into AcquiringBank table");
            return true;
        } catch (Exception e) {
            log.error("Error inserting default values into AcquiringBank table: {}", e.getMessage());
            return false;
        }
    }

}
