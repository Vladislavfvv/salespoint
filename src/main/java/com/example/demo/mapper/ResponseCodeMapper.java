package com.example.demo.mapper;

import com.example.demo.dto.ResponseCodeDto;
import com.example.demo.model.ResponseCode;

public interface ResponseCodeMapper {
    ResponseCodeDto toDto(ResponseCode responseCode);
    ResponseCode toEntity(ResponseCodeDto responseCode);
}
