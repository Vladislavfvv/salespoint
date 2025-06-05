package com.edme.salespoint.service;


import com.edme.salespoint.dto.ResponseCodeDto;
import com.edme.salespoint.mapper.ResponseCodeMapper;
import com.edme.salespoint.repository.ResponseCodeRepository;
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
public class ResponseCodeService {
    @Autowired
    private final ResponseCodeRepository responseCodeRepository;
    @Autowired
    private final ResponseCodeMapper responseCodeMapper;

    public List<ResponseCodeDto> findAll() {
        return responseCodeRepository.findAll()
                .stream().map(responseCodeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ResponseCodeDto> findById(Long id) {
        return responseCodeRepository.findById(id)
                .map(responseCodeMapper::toDto);
    }

    @Transactional
    public Optional<ResponseCodeDto> save(ResponseCodeDto dto) {
        Optional<ResponseCodeDto> exiting = findById(dto.getId());
        if (exiting.isPresent()) {
            log.info("Response code already exists: {}", dto);
            return Optional.empty();
        }
        dto.setId(null);
        responseCodeRepository.saveAndFlush(responseCodeMapper.toEntity(dto));
        return Optional.of(dto);
    }

    @Transactional
    public Optional<ResponseCodeDto> update(Long id, ResponseCodeDto dto) {
        Optional<ResponseCodeDto> exiting = findById(id);
        if (exiting.isPresent()) {
            ResponseCodeDto exitingDto = exiting.get();
            exitingDto.setErrorCode(dto.getErrorCode());
            exitingDto.setErrorLevel(exitingDto.getErrorLevel());
            exitingDto.setErrorDescription(dto.getErrorDescription());
            responseCodeRepository.save(responseCodeMapper.toEntity(dto));
            log.info("ResponseCode bank {} updated", exitingDto.getId());
            return Optional.of(exitingDto);
        } else {
            log.info("Response code not found: {}", dto);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<ResponseCodeDto> exiting = findById(id);
        if (exiting.isPresent()) {
            responseCodeRepository.deleteById(id);
            return true;
        }
        log.info("Response code not found: {}", id);
        return false;
    }

    @Transactional
    public boolean deleteAll() {
        try {
            responseCodeRepository.deleteAll();
            log.info("All ResponseCodeBanks deleted");
            return true;
        }catch (Exception e) {
            log.info("Error deleting all ResponseCodeBanks: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try{
            responseCodeRepository.dropTable();
            log.info("Table ResponseCodeBank dropped");
            return true;
        }catch (Exception e) {
            log.info("Error dropping Table ResponseCodeBank: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            responseCodeRepository.createTable();
            log.info("Table ResponseCodeBank created");
            return true;
        }catch (Exception e) {
            log.info("Error creating Table ResponseCodeBank: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if(!createTable()) {
            log.info("Initialization failed. Table ResponseCodeBank not created");
            return false;
        }
        try{
            responseCodeRepository.insertDefaultValues();
            log.info("Table ResponseCodeBank initialized");
            return true;
        }catch (Exception e) {
            log.info("Error initializing Table ResponseCodeBank: {}", e.getMessage());
            return false;
        }
    }
}
