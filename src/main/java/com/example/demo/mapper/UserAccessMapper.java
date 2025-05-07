package com.example.demo.mapper;

import com.example.demo.dto.UserAccessDto;
import com.example.demo.model.UserAccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccessMapper {
    UserAccess toEntity(UserAccessDto userAccessDto);
    UserAccessDto toDto(UserAccess userAccess);
}
