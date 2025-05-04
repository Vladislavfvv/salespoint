package com.example.demo.mapper;

import com.example.demo.dto.TerminalDto;
import com.example.demo.model.Terminal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TerminalMapper {
    @Mapping(source = "mcc.id", target = "merchantCategoryCodeId")
    @Mapping(source = "salesPoint.id", target = "salesPointId")
    TerminalDto toDto(Terminal terminal);

    @Mapping(source = "merchantCategoryCodeId", target = "mcc.id")
    @Mapping(source = "salesPointId", target = "salesPoint.id")
    Terminal toEntity(TerminalDto terminalDto);
}
