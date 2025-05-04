package com.example.demo.mapper;

import com.example.demo.dto.TransactionTypeDto;
import com.example.demo.model.TransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    @Mapping(target = "transactionTypeName", source = "transactionTypeName")
    TransactionTypeDto toDto(TransactionType transactionType);
    @Mapping(target = "transactionTypeName", source = "transactionTypeName")
    TransactionType toEntity(TransactionTypeDto transactionTypeDto);
}
