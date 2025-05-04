package com.example.demo.mapper;

import com.example.demo.dto.CardDto;
import com.example.demo.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardDto cardDto);
    CardDto toDto(Card card);

}
