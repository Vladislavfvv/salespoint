package com.example.demo.mapper;


import com.example.demo.dto.SalesPointDto;
import com.example.demo.model.SalesPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalesPointMapper {
    @Mapping(source = "acquiringBankId", target = "acquiringBank.id")
    SalesPoint toEntity(SalesPointDto salesPointDto);

    @Mapping(source = "acquiringBank.id", target = "acquiringBankId")
    SalesPointDto toDto(SalesPoint salesPoint);
}
