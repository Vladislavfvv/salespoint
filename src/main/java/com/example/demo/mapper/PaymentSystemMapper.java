package com.example.demo.mapper;

import com.example.demo.dto.PaymentSystemDto;
import com.example.demo.model.PaymentSystem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentSystemMapper {
    PaymentSystem toEntity(PaymentSystemDto paymentSystemDto);
    PaymentSystemDto toDto(PaymentSystem paymentSystem);
}
