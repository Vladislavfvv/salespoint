package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.AcquiringBankDto;
import com.edme.salespoint.model.AcquiringBank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcquiringBankMapper {
    AcquiringBankDto toDto(AcquiringBank entity);
    AcquiringBank toEntity(AcquiringBankDto dto);

}
