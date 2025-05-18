package com.example.demo.service;

import com.example.demo.dto.AcquiringBankDto;
import com.example.demo.mapper.AcquiringBankMapper;
import com.example.demo.model.AcquiringBank;
import com.example.demo.repository.AcquiringBankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    // Кеширует список всех банков
    @Cacheable(value = "allBanksCache")
    public List<AcquiringBankDto> findAll() {
        simulateSlowService(); // (для демонстрации задержки)
        return acquiringBankRepository.findAll()
                .stream()
                .map(acquiringBankMapper::toDto)
                .collect(Collectors.toList());
    }

    // Задержка для демонстрации кеша
    private void simulateSlowService() {
        long start = System.currentTimeMillis();
        log.info("Slow getting info from service. Start time: {}", start);
        try {
            Thread.sleep(5000L);
            long end = System.currentTimeMillis();
            log.info("Slow getting info from service. End time{}", end);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    // Кеширует банк по id
    // Если уже есть результат — возвращает его из кеша.
    @Cacheable(value = "bankByIdCache", key = "#id")
    public Optional<AcquiringBankDto> findById(Long id) {
        simulateSlowService(); // (для демонстрации задержки)
        return acquiringBankRepository.findById(id)
                .map(acquiringBankMapper::toDto);
    }

    @Transactional
    // Удаление из кеша при сохранении нового банка
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
    public Optional<AcquiringBankDto> save(AcquiringBankDto dto) {
//        Optional<AcquiringBankDto> exiting = findById(dto.getId());
//        if (exiting.isPresent()) {
//            log.info("Acquiring bank {} already exists", dto.getId());
//            return Optional.empty();
//        }
//        dto.setId(null);
//        acquiringBankRepository.saveAndFlush(acquiringBankMapper.toEntity(dto));
//        log.info("Acquiring bank {} saved", dto.getId());
//        return Optional.of(dto);
        // Если ID задан — проверяем, не существует ли уже банк
        Optional<AcquiringBank> exiting = acquiringBankRepository.findByBic(dto.getBic());
        if (exiting.isPresent()) {
            log.info("Acquiring bank with ID {} already exists", dto.getId());
            return Optional.empty();
        }
//        if (dto.getId() != null && acquiringBankRepository.findByBic(dto.getBic())) {
//        //if (dto.getId() != null && acquiringBankRepository.existsById(dto.getId())) {
//            log.info("Acquiring bank with ID {} already exists", dto.getId());
//            return Optional.empty();
//        }

        // Преобразуем DTO в сущность и сохраняем
        AcquiringBank entity = acquiringBankMapper.toEntity(dto);
        entity.setId(null); // Явно обнуляем ID, чтобы JPA сгенерировала новый

        AcquiringBank saved = acquiringBankRepository.saveAndFlush(entity); // теперь saved содержит сгенерированный ID

        log.info("Acquiring bank {} saved", saved.getId());
        return Optional.of(acquiringBankMapper.toDto(saved));
    }

//    @Transactional
//    public Optional<AcquiringBankDto> update(Long id, AcquiringBankDto dto) {
//        Optional<AcquiringBankDto> existing = findById(id);
//        if (existing.isPresent()) {
//            AcquiringBankDto entityDto = existing.get();
//
//            //  entityDto.setId(null);
//            entityDto.setBic(dto.getBic());
//            entityDto.setAbbreviatedName(dto.getAbbreviatedName());
//            acquiringBankRepository.save(acquiringBankMapper.toEntity(entityDto));
//            log.info("Acquiring bank {} updated", entityDto.getId());
//            return Optional.of(entityDto);
//        } else {
//            log.info("Acquiring bank {} not found", id);
//            return Optional.empty();
//        }
//    }

    @Transactional
    // Очистка кеша при обновлении
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
    public Optional<AcquiringBankDto> update(Long id, AcquiringBankDto dto) {
        return acquiringBankRepository.findById(id).map(entity -> {
            entity.setBic(dto.getBic());
            entity.setAbbreviatedName(dto.getAbbreviatedName());
            AcquiringBank saved = acquiringBankRepository.save(entity);
            log.info("Acquiring bank {} updated", saved.getId());
            return acquiringBankMapper.toDto(saved);
        });
    }

    @Transactional
    // Очистка кеша при удалении
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
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
    // Очистка кеша при удалении
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
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
    // Очистка кеша при удалении
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
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
    // Удаляет из кеша (частично или полностью) при изменении данных.
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
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
    // Очистка кеша при удалении
    @CacheEvict(value = {"allBanksCache", "bankByIdCache"}, allEntries = true)
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
