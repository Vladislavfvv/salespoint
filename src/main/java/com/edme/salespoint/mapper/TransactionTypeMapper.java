package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.TransactionTypeDto;
import com.edme.salespoint.model.TransactionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    TransactionTypeDto toDto(TransactionType transactionType);
    TransactionType toEntity(TransactionTypeDto transactionTypeDto);
}
