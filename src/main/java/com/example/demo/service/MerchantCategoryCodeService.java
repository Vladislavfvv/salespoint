package com.example.demo.service;

import com.example.demo.dto.MerchantCategoryCodeDto;
import com.example.demo.mapper.MerchantCategoryCodeMapper;
import com.example.demo.repository.MerchantCategoryCodeRepository;
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
public class MerchantCategoryCodeService {
    @Autowired
    private MerchantCategoryCodeRepository merchantCategoryCodeRepository;
    @Autowired
    private final MerchantCategoryCodeMapper merchantCategoryCodeMapper;

    public List<MerchantCategoryCodeDto> findAll() {
        return merchantCategoryCodeRepository.findAll()
                .stream()
                .map(merchantCategoryCodeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<MerchantCategoryCodeDto> findById(Long id) {
        return merchantCategoryCodeRepository.findById(id)
                .map(merchantCategoryCodeMapper::toDto);
    }

    @Transactional
    public Optional<MerchantCategoryCodeDto> save(MerchantCategoryCodeDto merchantCategoryCodeDto) {
        Optional<MerchantCategoryCodeDto> existing = findById(merchantCategoryCodeDto.getId());
        if (existing.isPresent()) {
            log.info("MerchantCategoryCodeDto {} already exists.", merchantCategoryCodeDto);
            return Optional.empty();
        }
        else {
            merchantCategoryCodeRepository.saveAndFlush(merchantCategoryCodeMapper.toEntity(merchantCategoryCodeDto));
            log.info("MerchantCategoryCodeDto {} saved.", merchantCategoryCodeDto);
            return Optional.of(merchantCategoryCodeDto);
        }
    }

    @Transactional
    public Optional<MerchantCategoryCodeDto> update(Long id, MerchantCategoryCodeDto dto) {
        Optional<MerchantCategoryCodeDto> existing = findById(id);
        if (existing.isPresent()) {
            MerchantCategoryCodeDto merchantCategoryCodeToUpdate = existing.get();
            merchantCategoryCodeToUpdate.setId(null);
            merchantCategoryCodeToUpdate.setMcc(dto.getMcc());
            merchantCategoryCodeToUpdate.setMccName(dto.getMccName());
            merchantCategoryCodeRepository.save(merchantCategoryCodeMapper.toEntity(merchantCategoryCodeToUpdate));
            log.info("MerchantCategoryCodeDto {} updated.", merchantCategoryCodeToUpdate);
            return Optional.of(merchantCategoryCodeToUpdate);
        } else {
            log.info("MerchantCategoryCodeDto {} not found.", id);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<MerchantCategoryCodeDto> existing = findById(id);
        if (existing.isPresent()) {
            merchantCategoryCodeRepository.deleteById(id);
            log.info("MerchantCategoryCodeDto {} deleted.", id);
            return true;
        } else {
            log.info("MerchantCategoryCodeDto {} not found.", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            merchantCategoryCodeRepository.deleteAll();
            log.info("MerchantCategoryCodeDto {} deleted.", merchantCategoryCodeRepository.findAll().size());
            return true;
        }catch(Exception e) {
            log.info("Error deleting all MerchantCategoryCodeDto {}" , e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            merchantCategoryCodeRepository.dropTable();
            log.info("Table MerchantCategoryCodeDto dropped");
            return true;
        }
        catch(Exception e) {
            log.info("Error dropping Table MerchantCategoryCodeDto {}" , e.getMessage());
        return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try{
            merchantCategoryCodeRepository.createTable();
            log.info("Table MerchantCategoryCodeDto created");
            return true;
        }catch(Exception e) {
            log.info("Error creating Table MerchantCategoryCodeDto {}" , e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try{
            merchantCategoryCodeRepository.insertDefaultValues();
            log.info("Table MerchantCategoryCodeDto initialized");
            return true;
        }catch(Exception e) {
            log.info("Error initializing Table MerchantCategoryCodeDto {}" , e.getMessage());
            return false;
        }
    }

}
