package com.edme.salespoint.mapper;

import com.edme.commondto.dto.TransactionExchangeDto;
import com.edme.salespoint.dto.TransactionDto;
import com.edme.salespoint.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionExchangeMapper {
    @Mapping(source = "transactionTypeId", target = "transactionType.id")
    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "terminalId", target = "terminal.id")
    @Mapping(source = "responseCodeId", target = "responseCode.id")
    TransactionDto toTransactionDto(TransactionExchangeDto dto);

    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "terminal.id", target = "terminalId")
    @Mapping(source = "responseCode.id", target = "responseCodeId")
    TransactionExchangeDto toExchangeDto(TransactionDto dto);
}
