package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.MerchantCategoryCodeDto;
import com.edme.salespoint.model.MerchantCategoryCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantCategoryCodeMapper {
    MerchantCategoryCode toEntity(MerchantCategoryCodeDto merchantCategoryCodeDto);
    MerchantCategoryCodeDto toDto(MerchantCategoryCode merchantCategoryCode);
}
