package com.example.demo.service;

import com.example.demo.dto.SalesPointDto;
import com.example.demo.mapper.SalesPointMapper;
import com.example.demo.model.SalesPoint;
import com.example.demo.repository.AcquiringBankRepository;
import com.example.demo.repository.SalesPointRepository;
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
public class SalesPointService {

    @Autowired
    private SalesPointRepository salesPointRepository;
    @Autowired
    private SalesPointMapper salesPointMapper;
    @Autowired
    private AcquiringBankRepository acquiringBankRepository;

    public List<SalesPointDto> findAll() {
        return salesPointRepository.findAll()
                .stream().map(salesPointMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SalesPointDto> findById(Long id) {
        return salesPointRepository.findById(id)
                .map(salesPointMapper::toDto);
    }

    public Optional<SalesPointDto> findByPosInn(String posInn) {
        return salesPointRepository.findByPosInn(posInn)
                .map(salesPointMapper::toDto);
    }

    @Transactional
    public Optional<SalesPointDto> save(SalesPointDto dto){
        Optional<SalesPointDto> exists = findByPosInn(dto.getPosInn());
        if(exists.isPresent()){
            log.info("SalesPoint already exists {}", dto);
            return Optional.empty();
        }
        else {
            dto.setId(null);

            SalesPoint forSave = salesPointMapper.toEntity(dto);
            SalesPoint saved = salesPointRepository.saveAndFlush(forSave);

            log.info("Saved SalesPoint {}", dto);
            return Optional.of(salesPointMapper.toDto(saved));
        }
    }

    @Transactional
    public Optional<SalesPointDto> update(Long id, SalesPointDto dto) {
        Optional<SalesPointDto> exists = findById(id);
        if(exists.isPresent()){
            SalesPointDto forSave = exists.get();

            forSave.setId(id);
            forSave.setPosName(dto.getPosName());
            forSave.setPosAddress(dto.getPosAddress());
            forSave.setPosInn(dto.getPosInn());
            SalesPoint updated = salesPointRepository.save(salesPointMapper.toEntity(forSave));
            log.info("Updated SalesPoint {}", updated);
            return Optional.of(salesPointMapper.toDto(updated));
        } else {
            log.info("SalesPoint not found {}", dto);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<SalesPointDto> exists = findById(id);
        if(exists.isPresent()){
            salesPointRepository.deleteById(id);
            log.info("Deleted SalesPoint {}", exists);
            return true;
        } else {
            log.info("SalesPoint not found {}", id);
            return false;
        }
    }

    public boolean deleteAll() {
        try {
            salesPointRepository.deleteAll();
            log.info("All records from table SalesPoint deleted");
            return true;
        } catch (Exception e) {
            log.error("Error deleting all records from table SalesPoint: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try{
            salesPointRepository.dropTable();
            log.info("Dropped table SalesPoint successfully");
            return true;
        } catch (Exception e) {
            log.info("Dropped table SalesPoint failed");
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            salesPointRepository.createTable();
            log.info("Created table SalesPoint successfully");
            return true;
        } catch (Exception e) {
            log.info("Created table SalesPoint failed");
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if(!createTable()){
            log.info("Initialization failed. Table SalesPoint not created");
            return false;
        }
        try{
            salesPointRepository.insertDefaultValues();
            log.info("Initialized table SalesPoint successfully");
            return true;
        } catch (Exception e) {
            log.info("Initialized table SalesPoint failed");
            return false;
        }
    }


}
