package com.example.demo.mapper;

import com.example.demo.dto.MerchantCategoryCodeDto;
import com.example.demo.model.MerchantCategoryCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantCategoryCodeMapper {
    MerchantCategoryCode toEntity(MerchantCategoryCodeDto merchantCategoryCodeDto);
    MerchantCategoryCodeDto toDto(MerchantCategoryCode merchantCategoryCode);
}
