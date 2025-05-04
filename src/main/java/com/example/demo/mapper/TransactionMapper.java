package com.example.demo.mapper;

import com.example.demo.dto.TransactionDto;
import com.example.demo.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "terminal.id", target = "terminalId")
    @Mapping(source = "responseCode.id", target = "responseCodeId")
    TransactionDto toDto(Transaction transaction);

    @Mapping(source = "transactionTypeId", target = "transactionType.id")
    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "terminalId", target = "terminal.id")
    @Mapping(source = "responseCodeId", target = "responseCode.id")
    Transaction toEntity(TransactionDto transactionDto);
}
