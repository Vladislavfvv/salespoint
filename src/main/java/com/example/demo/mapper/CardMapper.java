package com.example.demo.mapper;

import com.example.demo.dto.CardDto;
import com.example.demo.model.Card;
import com.example.demo.model.PaymentSystem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface CardMapper {
//    @Mapping(source = "paymentSystem.id", target = "paymentSystemId")
//    CardDto toDto(Card card);
//
//    @Mapping(target = "paymentSystem", expression = "java(mapPaymentSystem(cardDto.getPaymentSystemId()))")
//    Card toEntity(CardDto cardDto);

    @Mapping(source = "paymentSystem", target = "paymentSystem")
    CardDto toDto(Card card);

    @Mapping(source = "paymentSystem.id", target = "paymentSystem.id")
    Card toEntity(CardDto cardDto);

    default PaymentSystem mapPaymentSystem(Long id) {
        if(id == null) return null;
        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(id);
        return paymentSystem;
    }

}
