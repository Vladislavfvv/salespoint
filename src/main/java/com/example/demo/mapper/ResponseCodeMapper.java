package com.example.demo.mapper;

import com.example.demo.dto.ResponseCodeDto;
import com.example.demo.model.ResponseCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseCodeMapper {
    ResponseCodeDto toDto(ResponseCode responseCode);
    ResponseCode toEntity(ResponseCodeDto responseCode);
}
