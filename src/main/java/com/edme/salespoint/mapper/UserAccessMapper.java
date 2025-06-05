package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.UserAccessDto;
import com.edme.salespoint.model.UserAccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccessMapper {
    UserAccess toEntity(UserAccessDto userAccessDto);
    UserAccessDto toDto(UserAccess userAccess);
}
