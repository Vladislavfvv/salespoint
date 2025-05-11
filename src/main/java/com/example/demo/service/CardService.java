package com.example.demo.service;

import com.example.demo.dto.CardDto;
import com.example.demo.mapper.CardMapper;
import com.example.demo.model.Card;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.PaymentSystemRepository;
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
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private PaymentSystemRepository paymentSystemRepository;

    public List<CardDto> findAll() {
        return cardRepository.findAll()
                .stream().map(cardMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CardDto> findById(Long id) {
        return cardRepository.findById(id)
                .map(cardMapper::toDto);
    }

    public Optional<CardDto> findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .map(cardMapper::toDto);
    }

    @Transactional
    public Optional<CardDto> save(CardDto dto) {
        //  Optional<CardDto> card = findById(dto.getId());
          Optional<CardDto> card = findByCardNumber(dto.getCardNumber());
        if (card.isPresent()) {
            log.info("Card already exists: {}", dto);
            return Optional.empty();
        } else {
//            log.info("Card created: {}", dto);
//            cardRepository.saveAndFlush(cardMapper.toEntity(dto));
//            return Optional.of(dto);
            // Обнуляем ID, чтобы не было попытки обновить несуществующую запись
            dto.setId(null);

            Card entity = cardMapper.toEntity(dto);
            Card saved = cardRepository.saveAndFlush(entity);

            log.info("Card created: {}", saved);
            return Optional.of(cardMapper.toDto(saved));
        }
    }

    @Transactional
    public Optional<CardDto> update(Long id, CardDto dto) {
        Optional<CardDto> existing = findById(id);
        if (existing.isPresent()) {
            CardDto cardDto = existing.get();

            cardDto.setId(dto.getId());
            cardDto.setCardNumber(dto.getCardNumber());
            cardDto.setExpirationDate(dto.getExpirationDate());
            cardDto.setHolderName(dto.getHolderName());
//            if (paymentSystemRepository.findById(dto.getPaymentSystemId()).isPresent()) {
//                cardDto.setPaymentSystemId(dto.getPaymentSystemId());
//            } else cardDto.setPaymentSystemId(null);
            cardDto.setPaymentSystem(dto.getPaymentSystem());
            // - где-то здесь нужно проверку есть ли такой PaymentSystem перед сохранением
            cardRepository.save(cardMapper.toEntity(dto));
            log.info("Card updated: {}", dto);
            return Optional.of(dto);
        } else {
            log.info("Card not found: {}", dto);
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<CardDto> card = findById(id);
        if (card.isPresent()) {
            cardRepository.deleteById(id);
            log.info("Card deleted: {}", card);
            return true;
        } else {
            log.info("Card not found: {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            cardRepository.deleteAll();
            log.info("Card deleted all");
            return true;
        } catch (Exception e) {
            log.error("Error while deleting all cards", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            cardRepository.dropTable();
            log.info("Card drop table dropped");
            return true;
        } catch (Exception e) {
            log.error("Error while dropping table", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try{
            cardRepository.createTable();
            log.info("Card created table");
            return true;
        }catch (Exception e){
            log.error("Error while creating table", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            log.info("Initialization failed. Table Card not created");
            return false;
        }
        try {
            cardRepository.insertDefaultValues();
            log.info("Card initialized");
            return true;
        }catch (Exception e){
            log.error("Error while initializing table", e.getMessage());
            return false;
        }
    }

}
