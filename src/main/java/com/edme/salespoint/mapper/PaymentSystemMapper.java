package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.PaymentSystemDto;
import com.edme.salespoint.model.PaymentSystem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentSystemMapper {
    PaymentSystem toEntity(PaymentSystemDto paymentSystemDto);
    PaymentSystemDto toDto(PaymentSystem paymentSystem);
}
