package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.ResponseCodeDto;
import com.edme.salespoint.model.ResponseCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseCodeMapper {
    ResponseCodeDto toDto(ResponseCode responseCode);
    ResponseCode toEntity(ResponseCodeDto responseCode);
}
