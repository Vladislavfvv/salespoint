package com.example.demo.service;

import com.example.demo.dto.AcquiringBankDto;
import com.example.demo.model.AcquiringBank;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AbstractService<T, E> {

    public List<E> findAll();

    public Optional<E> findById(T t);

    public Optional<E> save(E dto);

    public Optional<E> update(T id, E dto);

    public boolean delete(T id);

    public boolean deleteAll();

    public boolean dropTable();

    public boolean createTable();

    public boolean initializeTable();
}

