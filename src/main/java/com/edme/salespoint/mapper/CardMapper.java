package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.CardDto;
import com.edme.salespoint.model.Card;
import com.edme.salespoint.model.PaymentSystem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PaymentSystemMapper.class})
public interface CardMapper {
//    @Mapping(source = "paymentSystem.id", target = "paymentSystemId")
//    CardDto toDto(Card card);
//
//    @Mapping(target = "paymentSystem", expression = "java(mapPaymentSystem(cardDto.getPaymentSystemId()))")
//    Card toEntity(CardDto cardDto);


    CardDto toDto(Card card);


    Card toEntity(CardDto cardDto);

    default PaymentSystem mapPaymentSystem(Long id) {
        if(id == null) return null;
        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(id);
        return paymentSystem;
    }

}
