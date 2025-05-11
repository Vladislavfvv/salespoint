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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class AcquiringBankServiceTest {

//    private static final AcquiringBank[] acquiringBanks = new AcquiringBank[]{
//            new AcquiringBank(1L, "041234567", "ПАО Банк-эквайер №1"),
//            new AcquiringBank(2L, "041234568", "ПАО Банк-эквайер №2"),
//            new AcquiringBank(3L, "041234569", "ПАО Банк-эквайер №3")
//    };
//
//
//    @Mock
//    private AcquiringBankRepository acquiringBankRepository;
//
//    @Mock
//    private AcquiringBankMapper acquiringBankMapper;
//
//    @InjectMocks
//    private AcquiringBankService acquiringBankService;
//
//    private AcquiringBankDto sampleDto;
//    private AcquiringBank sampleEntity;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        sampleDto = new AcquiringBankDto();
//        sampleDto.setId(1L);
//        sampleDto.setBic("BIC123");
//        sampleDto.setAbbreviatedName("Bank");
//
//        sampleEntity = new AcquiringBank();
//        sampleEntity.setId(1L);
//        sampleEntity.setBic("BIC123");
//        sampleEntity.setAbbreviatedName("Bank");
//    }
//
//    @Test
//    void testFindAll() {
//        when(acquiringBankRepository.findAll()).thenReturn(List.of(sampleEntity));
//        when(acquiringBankMapper.toDto(sampleEntity)).thenReturn(sampleDto);
//
//        List<AcquiringBankDto> result = acquiringBankService.findAll();
//
//        assertEquals(1, result.size());
//        assertEquals(sampleDto.getId(), result.get(0).getId());
//    }
//
//    @Test
//    void testFindById_found() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
//        when(acquiringBankMapper.toDto(sampleEntity)).thenReturn(sampleDto);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.findById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(sampleDto.getId(), result.get().getId());
//    }
//
//    @Test
//    void testFindById_notFound() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Optional<AcquiringBankDto> result = acquiringBankService.findById(1L);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testSave_newEntity() {
//        when(acquiringBankRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(acquiringBankMapper.toEntity(any())).thenReturn(sampleEntity);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.save(sampleDto);
//
//        assertTrue(result.isPresent());
//        verify(acquiringBankRepository).saveAndFlush(any(AcquiringBank.class));
//    }
//
//    @Test
//    void testSave_alreadyExists() {
//        when(acquiringBankRepository.findById(sampleDto.getId())).thenReturn(Optional.of(sampleEntity));
//
//        Optional<AcquiringBankDto> result = acquiringBankService.save(sampleDto);
//
//        assertFalse(result.isPresent());
//        verify(acquiringBankRepository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    void testUpdate_success() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
//        when(acquiringBankMapper.toEntity(any())).thenReturn(sampleEntity);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.update(1L, sampleDto);
//
//        assertTrue(result.isPresent());
//        verify(acquiringBankRepository).save(any(AcquiringBank.class));
//    }
//
//    @Test
//    void testUpdate_notFound() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Optional<AcquiringBankDto> result = acquiringBankService.update(1L, sampleDto);
//
//        assertFalse(result.isPresent());
//        verify(acquiringBankRepository, never()).save(any());
//    }
//
//    @Test
//    void testDelete_found() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
//
//        boolean result = acquiringBankService.delete(1L);
//
//        assertTrue(result);
//        verify(acquiringBankRepository).deleteById(1L);
//    }
//
//    @Test
//    void testDelete_notFound() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.empty());
//
//        boolean result = acquiringBankService.delete(1L);
//
//        assertFalse(result);
//        verify(acquiringBankRepository, never()).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteAll_success() {
//        doNothing().when(acquiringBankRepository).deleteAll();
//
//        boolean result = acquiringBankService.deleteAll();
//
//        assertTrue(result);
//    }
//
////    @Test
////    void testDeleteAll_exception() {
////        doThrow(new RuntimeException("error")).when(acquiringBankRepository).deleteAll();
////
////        boolean result = acquiringBankService.deleteAll();
////
////        assertFalse(result);
////    }
//
////    @Test
////    void testDeleteAll_exception() {
////        acquiringBankRepository = mock(AcquiringBankRepository.class);
////        acquiringBankService = new AcquiringBankService(acquiringBankRepository);
////
////        boolean result = acquiringBankService.dropTable();
////
////        assertTrue(result);
////        verify(acquiringBankRepository).dropTable();
////    }
//
//    @Test
//    void testDropTable_success() {
//        AcquiringBankRepository acquiringBankRepository = mock(AcquiringBankRepository.class);
//        AcquiringBankMapper mockMapper = mock(AcquiringBankMapper.class);
//
//        AcquiringBankService acquiringBankService = new AcquiringBankService(acquiringBankRepository, mockMapper);
//
//        when(acquiringBankRepository.dropTable()).thenReturn(null);
//
//        boolean result = acquiringBankService.dropTable();
//
//        assertTrue(result);
//        verify(acquiringBankRepository).dropTable();
//    }
//
////    @Test
////    void testDropTable_success() {
////        doNothing().when(acquiringBankRepository).dropTable();
////
////        boolean result = acquiringBankService.dropTable();
////
////        assertTrue(result);
////    }
//
//    @Test
//    void testDropTable_exception() {
//        doThrow(new RuntimeException("error")).when(acquiringBankRepository).dropTable();
//
//        boolean result = acquiringBankService.dropTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testCreateTable_success() {
//        doNothing().when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.createTable();
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testCreateTable_exception() {
//        doThrow(new RuntimeException("error")).when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.createTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testInitializeTable_success() {
//        doNothing().when(acquiringBankRepository).createTable();
//        doNothing().when(acquiringBankRepository).insertDefaultValues();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testInitializeTable_createFails() {
//        doThrow(new RuntimeException("fail")).when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testInitializeTable_insertFails() {
//        doNothing().when(acquiringBankRepository).createTable();
//        doThrow(new RuntimeException("fail")).when(acquiringBankRepository).insertDefaultValues();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertFalse(result);
//    }

//
//@Mock
//private AcquiringBankRepository acquiringBankRepository;
//
//    @Mock
//    private AcquiringBankMapper acquiringBankMapper;
//
//    @InjectMocks
//    private AcquiringBankService acquiringBankService;
//
//    private final AcquiringBank acquiringBank = new AcquiringBank(1L, "041234567", "ПАО Банк-эквайер №1");
//    private final AcquiringBankDto acquiringBankDto = new AcquiringBankDto(1L, "041234567", "ПАО Банк-эквайер №1");
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindAll() {
//        when(acquiringBankRepository.findAll()).thenReturn(List.of(acquiringBank));
//        when(acquiringBankMapper.toDto(acquiringBank)).thenReturn(acquiringBankDto);
//
//        List<AcquiringBankDto> result = acquiringBankService.findAll();
//
//        assertEquals(1, result.size());
//        assertEquals(acquiringBankDto.getId(), result.get(0).getId());
//    }
//
//    @Test
//    void testFindById_Found() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBank));
//        when(acquiringBankMapper.toDto(acquiringBank)).thenReturn(acquiringBankDto);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.findById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(1L, result.get().getId());
//    }
//
//    @Test
//    void testFindById_NotFound() {
//        when(acquiringBankRepository.findById(999L)).thenReturn(Optional.empty());
//
//        Optional<AcquiringBankDto> result = acquiringBankService.findById(999L);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testSave_NewEntity() {
//        AcquiringBankDto newDto = new AcquiringBankDto(null, "999999999", "Новый банк");
//        AcquiringBank newEntity = new AcquiringBank(null, "999999999", "Новый банк");
//
//        when(acquiringBankRepository.findById(null)).thenReturn(Optional.empty());
//        when(acquiringBankMapper.toEntity(newDto)).thenReturn(newEntity);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.save(newDto);
//
//        verify(acquiringBankRepository).saveAndFlush(newEntity);
//        assertTrue(result.isPresent());
//        assertNull(result.get().getId()); // ID ещё не задан, может быть null
//    }
//
//    @Test
//    void testSave_AlreadyExists() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBank));
//        when(acquiringBankMapper.toDto(acquiringBank)).thenReturn(acquiringBankDto);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.save(acquiringBankDto);
//
//        verify(acquiringBankRepository, never()).save(any());
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testUpdate_Exists() {
//        AcquiringBankDto updatedDto = new AcquiringBankDto(1L, "000000000", "Обновлённый банк");
//        AcquiringBank updatedEntity = new AcquiringBank(1L, "000000000", "Обновлённый банк");
//
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBank));
//        when(acquiringBankMapper.toDto(acquiringBank)).thenReturn(acquiringBankDto);
//        when(acquiringBankMapper.toEntity(any())).thenReturn(updatedEntity);
//
//        Optional<AcquiringBankDto> result = acquiringBankService.update(1L, updatedDto);
//
//        verify(acquiringBankRepository).save(any());
//        assertTrue(result.isPresent());
//        assertEquals("000000000", result.get().getBic());
//    }
//
//    @Test
//    void testUpdate_NotFound() {
//        AcquiringBankDto dto = new AcquiringBankDto(99L, "000", "Not found");
//
//        when(acquiringBankRepository.findById(99L)).thenReturn(Optional.empty());
//
//        Optional<AcquiringBankDto> result = acquiringBankService.update(99L, dto);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testDelete_Found() {
//        when(acquiringBankRepository.findById(1L)).thenReturn(Optional.of(acquiringBank));
//        when(acquiringBankMapper.toDto(acquiringBank)).thenReturn(acquiringBankDto);
//
//        boolean result = acquiringBankService.delete(1L);
//
//        verify(acquiringBankRepository).deleteById(1L);
//        assertTrue(result);
//    }
//
//    @Test
//    void testDelete_NotFound() {
//        when(acquiringBankRepository.findById(2L)).thenReturn(Optional.empty());
//
//        boolean result = acquiringBankService.delete(2L);
//
//        verify(acquiringBankRepository, never()).deleteById(any());
//        assertFalse(result);
//    }
//
//    @Test
//    void testDeleteAll() {
//        boolean result = acquiringBankService.deleteAll();
//
//        verify(acquiringBankRepository).deleteAll();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDropTable_Success() {
//        when(acquiringBankService.dropTable()).thenReturn(true);
//
//        boolean result = acquiringBankService.dropTable();
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testDropTable_Failure() {
//        doThrow(new RuntimeException("DB error")).when(acquiringBankRepository).dropTable();
//
//        boolean result = acquiringBankService.dropTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testCreateTable_Success() {
//        doNothing().when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.createTable();
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testCreateTable_Failure() {
//        doThrow(new RuntimeException("Error")).when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.createTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testInitializeTable_Success() {
//        doNothing().when(acquiringBankRepository).createTable();
//        when(acquiringBankService.initializeTable()).thenReturn(1);
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testInitializeTable_CreateFails() {
//        doThrow(new RuntimeException("fail")).when(acquiringBankRepository).createTable();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testInitializeTable_InsertFails() {
//        doNothing().when(acquiringBankRepository).createTable();
//        doThrow(new RuntimeException("fail")).when(acquiringBankRepository).insertDefaultValues();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertFalse(result);
//    }

@Mock
private AcquiringBankRepository acquiringBankRepository;

    @Mock
    private AcquiringBankMapper acquiringBankMapper;

    @InjectMocks
    private AcquiringBankService acquiringBankService;

    private static final AcquiringBank[] acquiringBanks = new AcquiringBank[]{
            new AcquiringBank(1L, "041234567", "ПАО Банк-эквайер №1"),
            new AcquiringBank(2L, "041234568", "ПАО Банк-эквайер №2"),
            new AcquiringBank(3L, "041234569", "ПАО Банк-эквайер №3")
    };

    private static final AcquiringBank[] mockAcquiringBanks = new AcquiringBank[]{
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

//    @Test
//    void dropTable_shouldReturnTrueOnSuccess() {
//        doNothing().when(acquiringBankRepository).dropTable();
//
//        boolean result = acquiringBankService.dropTable();
//
//        assertTrue(result);
//        verify(acquiringBankRepository).dropTable();
//    }

    @Test
    void createTable_shouldReturnTrueOnSuccess() {
        doNothing().when(acquiringBankRepository).createTable();

        boolean result = acquiringBankService.createTable();

        assertTrue(result);
        verify(acquiringBankRepository).createTable();
    }

//    @Test
//    void initializeTable_shouldInsertValuesWhenCreateTableSucceeds() {
//        doNothing().when(acquiringBankRepository).createTable();
//        doNothing().when(acquiringBankRepository).insertDefaultValues();
//
//        boolean result = acquiringBankService.initializeTable();
//
//        assertTrue(result);
//        verify(acquiringBankRepository).createTable();
//        verify(acquiringBankRepository).insertDefaultValues();
//    }

    @Test
    void initializeTable_shouldReturnFalseIfCreateFails() {
        doThrow(RuntimeException.class).when(acquiringBankRepository).createTable();

        boolean result = acquiringBankService.initializeTable();

        assertFalse(result);
        verify(acquiringBankRepository).createTable();
        verify(acquiringBankRepository, never()).insertDefaultValues();
    }
}
