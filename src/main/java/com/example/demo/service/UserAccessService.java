package com.example.demo.service;

import com.example.demo.dto.UserAccessDto;
import com.example.demo.mapper.UserAccessMapper;
import com.example.demo.repository.UserAccessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccessService {
    @Autowired
    private final UserAccessRepository userAccessRepository;
    @Autowired
    private final UserAccessMapper userAccessMapper;

    public List<UserAccessDto> findAll() {
        return userAccessRepository.findAll()
                .stream().map(userAccessMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserAccessDto> findById(Long id) {
        return userAccessRepository.findById(id)
                .map(userAccessMapper::toDto);
    }

    @Transactional
    public Optional<UserAccessDto> save(UserAccessDto dto) {
        Optional<UserAccessDto> exiting = findById(dto.getId());
        if (exiting.isPresent()) {
            log.info("User access already exists with id: {}", dto.getId());
            return Optional.empty();
        } else {
            dto.setId(null);
            userAccessRepository.saveAndFlush(userAccessMapper.toEntity(dto));
            log.info("User access successfully saved");
            return Optional.of(dto);
        }
    }

    @Transactional
    public Optional<UserAccessDto> update(Long id, UserAccessDto dto) {
        Optional<UserAccessDto> exiting = findById(id);
        if (exiting.isPresent()) {
            UserAccessDto exitingDto = exiting.get();
            // exiting.get().setId(dto.getId());
            exitingDto.setId(dto.getId());
            exitingDto.setUserLogin(dto.getUserLogin());
            exitingDto.setUserPassword(dto.getUserPassword());
            exitingDto.setFillName(dto.getFillName());
            exitingDto.setUserRole(dto.getUserRole());
            userAccessRepository.save(userAccessMapper.toEntity(exitingDto));
            log.info("UserAccess with id: {} successfully updated", dto.getId());
            return Optional.of(dto);
        } else {
            log.info("User access already exists with id: {}", id);
            return Optional.empty();
        }
    }


    @Transactional
    public boolean delete(Long id) {
        Optional<UserAccessDto> exiting = findById(id);
        if (exiting.isPresent()) {
            userAccessRepository.deleteById(id);
            log.info("UserAccess with id: {} successfully deleted", id);
            return true;
        } else {
            log.info("User access already exists with id: {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            userAccessRepository.deleteAll();
            log.info("UserAccess deleted successfully");
            return true;
        } catch (Exception e) {
            log.info("UserAccess could not be deleted");
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            userAccessRepository.deleteAll();
            log.info("UserAccess dropped successfully");
            return true;
        } catch (Exception e) {
            log.info("UserAccess could not be dropped");
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            userAccessRepository.createTable();
            log.info("UserAccess created successfully");
            return true;
        } catch (Exception e) {
            log.error("Error creating Table UserAccess {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }
        try {
            userAccessRepository.insertDefaultValues();
            log.info("UserAccess initialized successfully");
            return true;
        } catch (Exception e) {
            log.error("Error initializing Table UserAccess {}", e.getMessage());
            return false;
        }
    }
}
