package com.example.demo.service;

import com.example.demo.dto.TerminalDto;
import com.example.demo.mapper.TerminalMapper;
import com.example.demo.model.Terminal;
import com.example.demo.repository.TerminalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private TerminalMapper terminalMapper;

    public List<TerminalDto> findAll() {
        return terminalRepository.findAll()
                .stream().map(terminalMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TerminalDto> findById(Long id) {
        return terminalRepository.findById(id)
                .map(terminalMapper::toDto);
    }

    @Transactional
    public Optional<TerminalDto> save(TerminalDto dto) {
        Optional<Terminal> terminal = terminalRepository.findById(dto.getId());
        if (terminal.isPresent()) {
            log.info("Terminal already exists: {}", dto);
            return Optional.empty();
        } else {
            dto.setId(null);

            Terminal entity = terminalMapper.toEntity(dto);
            Terminal saved = terminalRepository.saveAndFlush(entity);

            log.info("Terminal saved: {}", entity);
            return Optional.of(terminalMapper.toDto(saved));
        }
    }

    @Transactional
    public Optional<TerminalDto> update(Long id, TerminalDto dto) {
        Optional<TerminalDto> existing = findById(id);
        if (existing.isPresent()) {
            TerminalDto terminalDto = existing.get();

            terminalDto.setId(dto.getId());
            terminalDto.setTerminalId(dto.getTerminalId());
            terminalDto.setMcc(dto.getMcc());
            terminalDto.setSalesPoint(dto.getSalesPoint());
            terminalRepository.save(terminalMapper.toEntity(terminalDto));
            log.info("Terminal updated: {}", terminalDto);
            return Optional.of(terminalDto);
        } else {
            log.info("Terminal not found: {}", dto);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<TerminalDto> terminal = findById(id);
        if (terminal.isPresent()) {
            terminalRepository.deleteById(id);
            log.info("Terminal deleted: {}", terminal);
            return true;
        } else {
            log.info("Terminal not found: {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll(){
         try{
             terminalRepository.deleteAll();
             log.info("All records from table Terminal deleted");
             return true;
         } catch (Exception e) {
             log.error("Error deleting all records from table Terminal: {}", e.getMessage());
             return false;
         }
    }

    @Transactional
    public boolean dropTable() {
        try {
            terminalRepository.dropTable();
            log.info("Dropped table Terminal successfully");
            return true;
        } catch (Exception e) {
            log.error("Error dropping table Terminal : {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try{
            terminalRepository.createTable();
            log.info("Created table Terminal successfully");
            return true;
        } catch (Exception e) {
            log.error("Error creating table Terminal : {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if(!createTable()){
            log.error("Error initializing table Terminal  cause table  Terminal not created");
            return false;
        }
        try{
            terminalRepository.insertDefaultValues();
            log.info("Initialized table Terminal successfully");
            return true;
        } catch (Exception e){
            log.error("Error initializing table Terminal : {}", e.getMessage());
            return false;
        }
    }

}
