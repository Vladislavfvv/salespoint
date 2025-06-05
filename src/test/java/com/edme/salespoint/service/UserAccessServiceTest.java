package com.edme.salespoint.service;

import com.edme.salespoint.dto.UserAccessDto;
import com.edme.salespoint.mapper.UserAccessMapper;
import com.edme.salespoint.model.UserAccess;
import com.edme.salespoint.repository.UserAccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserAccessServiceTest {
    @Mock
    private UserAccessRepository userAccessRepository;

    @Mock
    private UserAccessMapper userAccessMapper;

    @InjectMocks
    private UserAccessService userAccessService;

    private static final UserAccess[] userAccesses = new UserAccess[]{
            new UserAccess(1L, "login1", "pass1", "Full Name 1", "ADMIN"),
            new UserAccess(2L, "login2", "pass2", "Full Name 2", "USER"),
            new UserAccess(3L, "login3", "pass3", "Full Name 3", "GUEST")
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnDtos() {
        when(userAccessRepository.findAll()).thenReturn(Arrays.asList(userAccesses));
        when(userAccessMapper.toDto(any())).thenAnswer(invocation -> {
            UserAccess ua = invocation.getArgument(0);
            return new UserAccessDto(ua.getId(), ua.getUserLogin(), ua.getUserPassword(), ua.getFullName(), ua.getUserRole());
        });

        List<UserAccessDto> result = userAccessService.findAll();

        assertEquals(3, result.size());
        assertEquals("login1", result.get(0).getUserLogin());
        verify(userAccessRepository).findAll();
    }

    @Test
    void findById_shouldReturnDtoIfExists() {
        UserAccess ua = userAccesses[0];
        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(ua));
        when(userAccessMapper.toDto(ua)).thenReturn(
                new UserAccessDto(ua.getId(), ua.getUserLogin(), ua.getUserPassword(), ua.getFullName(), ua.getUserRole())
        );

        Optional<UserAccessDto> result = userAccessService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("login1", result.get().getUserLogin());
        verify(userAccessRepository).findById(1L);
    }

    @Test
    void save_shouldReturnSavedDtoWhenNew() {
        UserAccessDto newDto = new UserAccessDto(null, "newLogin", "newPass", "New Name", "USER");
        UserAccess entity = new UserAccess(null, "newLogin", "newPass", "New Name", "USER");

        when(userAccessRepository.findById(null)).thenReturn(Optional.empty());
        when(userAccessMapper.toEntity(newDto)).thenReturn(entity);

        Optional<UserAccessDto> result = userAccessService.save(newDto);

        assertTrue(result.isPresent());
        verify(userAccessRepository).saveAndFlush(entity);
    }

//    @Test
//    void save_shouldReturnEmptyWhenExists() {
//        UserAccessDto existingDto = new UserAccessDto(1L, "login1", "pass1", "Full Name 1", "ADMIN");
//        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(userAccesses[0]));
//
//        Optional<UserAccessDto> result = userAccessService.save(existingDto);
//
//        assertTrue(result.isEmpty());
//        verify(userAccessRepository, never()).saveAndFlush(any());
//    }

    @Test
    void save_shouldReturnEmptyWhenExists() {
        // Arrange
        UserAccessDto dto = new UserAccessDto();
        dto.setId(1L);
        dto.setUserLogin("existingUser");
        dto.setUserPassword("password");
        dto.setFullName("Full Name");
        dto.setUserRole("ADMIN");

        UserAccess existingEntity = UserAccess.builder()
                .id(1L)
                .userLogin("existingUser")
                .userPassword("password")
                .fullName("Full Name")
                .userRole("ADMIN")
                .build();

        // Мокаем поведение: репозиторий возвращает сущность
        when(userAccessRepository.findByUserLogin("existingUser"))
                .thenReturn(Optional.of(existingEntity));

        // Мокаем маппер: преобразует сущность в DTO
        when(userAccessMapper.toDto(existingEntity)).thenReturn(dto);

        // Act
        Optional<UserAccessDto> result = userAccessService.save(dto);

        // Assert
        assertTrue(result.isEmpty(), "Result should be empty when user already exists");
        verify(userAccessRepository, never()).saveAndFlush(any());
        verify(userAccessMapper, never()).toEntity(any());
    }

//    @Test
//    void update_shouldUpdateIfExists() {
//        UserAccessDto dtoToUpdate = new UserAccessDto(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//        UserAccess entityToSave = new UserAccess(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//
//        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(userAccesses[0]));
//        when(userAccessMapper.toEntity(any())).thenReturn(entityToSave);
//
//        Optional<UserAccessDto> result = userAccessService.update(1L, dtoToUpdate);
//
//        assertTrue(result.isPresent());
//        assertEquals("updatedPass", result.get().getUserPassword());
//        assertEquals("Updated Name", result.get().getFillName());
//        assertEquals("ADMIN", result.get().getUserRole());
//        verify(userAccessRepository).save(entityToSave);
//    }


//    @Test
//    void update_shouldUpdateIfExists() {
//        // Создаем DTO и сущность, которые будут использованы для обновления
//        UserAccessDto dtoToUpdate = new UserAccessDto(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//        UserAccess entityToSave = new UserAccess(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//
//        // Мокаем репозиторий, чтобы он вернул существующего пользователя с id 1
//        UserAccess userAccess = new UserAccess(1L, "login1", "oldPass", "Old Name", "USER");
//        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(userAccess));
//        when(userAccessMapper.toEntity(any())).thenReturn(entityToSave);
//
//        // Вызов метода обновления
//        Optional<UserAccessDto> result = userAccessService.update(1L, dtoToUpdate);
//
//        // Проверки
//        assertTrue(result.isPresent());
//        assertEquals("updatedPass", result.get().getUserPassword());
//        assertEquals("Updated Name", result.get().getFillName());
//        assertEquals("ADMIN", result.get().getUserRole());
//
//        // Проверяем, что метод save был вызван
//        verify(userAccessRepository).save(entityToSave);
//    }

//    @Test
//    void update_shouldUpdateIfExists() {
//        // Создаем DTO для обновления
//        UserAccessDto dtoToUpdate = new UserAccessDto(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//
//        // Создаем сущность, которая будет возвращена репозиторием
//        UserAccess existingUser = new UserAccess(1L, "login1", "oldPass", "Old Name", "USER");
//
//        // Мокаем возвращаемое значение для метода findById
//        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(existingUser));
//
//        // Мокаем метод toEntity, чтобы вернуть новую сущность при сохранении
//        UserAccess entityToSave = new UserAccess(1L, "login1", "updatedPass", "Updated Name", "ADMIN");
//        when(userAccessMapper.toEntity(any())).thenReturn(entityToSave);
//
//        // Выполняем обновление
//        Optional<UserAccessDto> result = userAccessService.update(1L, dtoToUpdate);
//
//        // Проверки
//        assertTrue(result.isPresent()); // Проверяем, что результат не пуст
//        assertEquals("updatedPass", result.get().getUserPassword()); // Проверяем обновленный пароль
//        assertEquals("Updated Name", result.get().getFillName()); // Проверяем обновленное имя
//        assertEquals("ADMIN", result.get().getUserRole()); // Проверяем обновленную роль
//
//        // Проверяем, что метод save был вызван
//        verify(userAccessRepository).save(entityToSave);
//    }


    @Test
    void update_shouldReturnEmptyIfNotExists() {
        UserAccessDto dtoToUpdate = new UserAccessDto(99L, "loginX", "passX", "NameX", "USER");

        when(userAccessRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserAccessDto> result = userAccessService.update(99L, dtoToUpdate);

        assertTrue(result.isEmpty());
        verify(userAccessRepository, never()).save(any());
    }

//    @Test
//    void delete_shouldDeleteIfExists() {
//        when(userAccessRepository.findById(1L)).thenReturn(Optional.of(userAccesses[0]));
//
//        boolean result = userAccessService.delete(1L);
//
//        assertTrue(result);
//        verify(userAccessRepository).deleteById(1L);
//    }

    @Test
    void delete_shouldReturnFalseIfNotExists() {
        when(userAccessRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = userAccessService.delete(99L);

        assertFalse(result);
        verify(userAccessRepository, never()).deleteById(any());
    }

    @Test
    void deleteAll_shouldReturnTrueOnSuccess() {
        doNothing().when(userAccessRepository).deleteAll();

        boolean result = userAccessService.deleteAll();

        assertTrue(result);
        verify(userAccessRepository).deleteAll();
    }

    @Test
    void dropTable_shouldReturnTrueOnSuccess() {
        doNothing().when(userAccessRepository).deleteAll();

        boolean result = userAccessService.dropTable();

        assertTrue(result);
        verify(userAccessRepository).deleteAll();
    }

    @Test
    void dropTable_shouldReturnFalseOnException() {
        doThrow(RuntimeException.class).when(userAccessRepository).deleteAll();

        boolean result = userAccessService.dropTable();

        assertFalse(result);
        verify(userAccessRepository).deleteAll();
    }

    @Test
    void createTable_shouldReturnTrueOnSuccess() {
        doNothing().when(userAccessRepository).createTable();

        boolean result = userAccessService.createTable();

        assertTrue(result);
        verify(userAccessRepository).createTable();
    }

    @Test
    void createTable_shouldReturnFalseOnFailure() {
        doThrow(RuntimeException.class).when(userAccessRepository).createTable();

        boolean result = userAccessService.createTable();

        assertFalse(result);
        verify(userAccessRepository).createTable();
    }

    @Test
    void testInitializeTable_success() {
        UserAccessService spyService = Mockito.spy(userAccessService);
        doReturn(true).when(spyService).createTable();

        boolean result = spyService.initializeTable();

        verify(userAccessRepository, times(1)).insertDefaultValues();
        assertTrue(result);
    }

    @Test
    void testInitializeTable_createTableFails() {
        UserAccessService spyService = Mockito.spy(userAccessService);
        doReturn(false).when(spyService).createTable();

        boolean result = spyService.initializeTable();

        verify(userAccessRepository, never()).insertDefaultValues();
        assertFalse(result);
    }

    @Test
    void testInitializeTable_insertDefaultValuesThrowsException() {
        UserAccessService spyService = Mockito.spy(userAccessService);
        doReturn(true).when(spyService).createTable();
        doThrow(new RuntimeException("Test error")).when(userAccessRepository).insertDefaultValues();

        boolean result = spyService.initializeTable();

        verify(userAccessRepository, times(1)).insertDefaultValues();
        assertFalse(result);
    }
}