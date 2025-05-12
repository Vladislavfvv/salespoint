package com.example.demo.service;

import com.example.demo.dto.AcquiringBankDto;
import com.example.demo.mapper.AcquiringBankMapper;
import com.example.demo.model.AcquiringBank;
import com.example.demo.repository.AcquiringBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class AcquiringBankServiceTest {

    @Mock
    private AcquiringBankRepository acquiringBankRepository;

    @Mock
    private AcquiringBankMapper acquiringBankMapper;

    @InjectMocks//класс который тестируем
    private AcquiringBankService acquiringBankService;

    private static final AcquiringBank[] acquiringBanks = new AcquiringBank[]{
            new AcquiringBank(1L, "041234567", "ПАО Банк-эквайер №1"),
            new AcquiringBank(2L, "041234568", "ПАО Банк-эквайер №2"),
            new AcquiringBank(3L, "041234569", "ПАО Банк-эквайер №3")
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnDtos() {
        when(acquiringBankRepository.findAll()).thenReturn(Arrays.asList(acquiringBanks));
        when(acquiringBankMapper.toDto(any())).thenAnswer(invocation -> {
            AcquiringBank bank = invocation.getArgument(0);
            return new AcquiringBankDto(bank.getId(), bank.getBic(), bank.getAbbreviatedName());
        });

        List<AcquiringBankDto> result = acquiringBankService.findAll();

        assertEquals(3, result.size());
        assertEquals("041234567", result.get(0).getBic());
        verify(acquiringBankRepository).findAll();
    }

    @Test
    void findById_shouldReturnDtoIfExists() {
        AcquiringBank bank = acquiringBanks[0];
        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(acquiringBankMapper.toDto(bank)).thenReturn(new AcquiringBankDto(bank.getId(), bank.getBic(), bank.getAbbreviatedName()));

        Optional<AcquiringBankDto> result = acquiringBankService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("041234567", result.get().getBic());
        verify(acquiringBankRepository).findById(1L);
    }

    @Test
    void save_shouldReturnSavedDtoWhenNew() {
        AcquiringBankDto newDto = new AcquiringBankDto(null, "123456789", "Новый банк");
        AcquiringBank entity = new AcquiringBank(null, "123456789", "Новый банк");

        when(acquiringBankRepository.findById(null)).thenReturn(Optional.empty());
        when(acquiringBankMapper.toEntity(newDto)).thenReturn(entity);

        Optional<AcquiringBankDto> saved = acquiringBankService.save(newDto);

        assertTrue(saved.isPresent());
        verify(acquiringBankRepository).saveAndFlush(entity);
    }

    @Test
    void save_shouldReturnEmptyWhenExists() {
        AcquiringBankDto existingDto = new AcquiringBankDto(1L, "041234567", "ПАО Банк");
        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBanks[0]));
        when(acquiringBankMapper.toDto(any())).thenReturn(existingDto);

        Optional<AcquiringBankDto> result = acquiringBankService.save(existingDto);

        assertTrue(result.isEmpty());
        verify(acquiringBankRepository, never()).saveAndFlush(any());
    }

    @Test
    void update_shouldUpdateIfExists() {
        AcquiringBankDto dtoToUpdate = new AcquiringBankDto(1L, "041200000", "Обновлённый банк");
        AcquiringBankDto existingDto = new AcquiringBankDto(1L, "041234567", "ПАО Банк");
        AcquiringBank entityToSave = new AcquiringBank(1L, "041200000", "Обновлённый банк");

        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBanks[0]));
        when(acquiringBankMapper.toDto(acquiringBanks[0])).thenReturn(existingDto);
        when(acquiringBankMapper.toEntity(any())).thenReturn(entityToSave);

        Optional<AcquiringBankDto> result = acquiringBankService.update(1L, dtoToUpdate);

        assertTrue(result.isPresent());
        assertEquals("041200000", result.get().getBic());
        verify(acquiringBankRepository).save(entityToSave);
    }

    @Test
    void delete_shouldDeleteIfExists() {
        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBanks[0]));
        when(acquiringBankMapper.toDto(acquiringBanks[0]))
                .thenReturn(new AcquiringBankDto(1L, "041234567", "ПАО Банк"));

        boolean result = acquiringBankService.delete(1L);

        assertTrue(result);
        verify(acquiringBankRepository).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalseIfNotExists() {
        when(acquiringBankRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = acquiringBankService.delete(99L);

        assertFalse(result);
        verify(acquiringBankRepository, never()).deleteById(any());
    }

    @Test
    void dropTable_shouldReturnTrueOnSuccess() {
        // doNothing().when(acquiringBankRepository).dropTable();
        doReturn(1).when(acquiringBankRepository).dropTable();
        boolean result = acquiringBankService.dropTable();

        assertTrue(result);
        verify(acquiringBankRepository).dropTable();
    }

    @Test
    void createTable_shouldReturnTrueOnSuccess() {
        doNothing().when(acquiringBankRepository).createTable();

        boolean result = acquiringBankService.createTable();

        assertTrue(result);
        verify(acquiringBankRepository).createTable();
    }

    @Test
    void initializeTable_shouldInsertValuesWhenCreateTableSucceeds() {
        doNothing().when(acquiringBankRepository).createTable();
        doNothing().when(acquiringBankRepository).insertDefaultValues();

        boolean result = acquiringBankService.initializeTable();

        assertTrue(result);
        verify(acquiringBankRepository).createTable();
        verify(acquiringBankRepository).insertDefaultValues();
    }

    @Test
    void initializeTable_shouldReturnFalseIfCreateFails() {
        doThrow(RuntimeException.class).when(acquiringBankRepository).createTable();

        boolean result = acquiringBankService.initializeTable();

        assertFalse(result);
        verify(acquiringBankRepository).createTable();
        verify(acquiringBankRepository, never()).insertDefaultValues();
    }
}
