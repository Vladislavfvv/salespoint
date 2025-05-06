package com.example.demo.mapper;

import com.example.demo.dto.TerminalDto;
import com.example.demo.model.MerchantCategoryCode;
import com.example.demo.model.SalesPoint;
import com.example.demo.model.Terminal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TerminalMapper {
    //    @Mapping(source = "mcc.id", target = "merchantCategoryCodeId")
//    @Mapping(source = "salesPoint.id", target = "salesPointId")
//    TerminalDto toDto(Terminal terminal);
//
//    @Mapping(source = "merchantCategoryCodeId", target = "mcc.id")
//    @Mapping(source = "salesPointId", target = "salesPoint.id")
//    Terminal toEntity(TerminalDto terminalDto);
    @Mapping(source = "mcc.id", target = "merchantCategoryCodeId")
    @Mapping(source = "salesPoint.id", target = "salesPointId")
    TerminalDto toDto(Terminal terminal);

    @Mapping(target = "mcc", expression = "java(mapMcc(terminalDto.getMerchantCategoryCodeId()))")
    @Mapping(target = "salesPoint", expression = "java(mapSalesPoint(terminalDto.getSalesPointId()))")
    Terminal toEntity(TerminalDto terminalDto);

    default MerchantCategoryCode mapMcc(Long id) {
        if (id == null) {
            return null;
        }
        MerchantCategoryCode mcc = new MerchantCategoryCode();
        mcc.setId(id);
        return mcc;
    }

    default SalesPoint mapSalesPoint(Long id) {
        if (id == null) {
            return null;
        }
        SalesPoint salesPoint = new SalesPoint();
        salesPoint.setId(id);
        return salesPoint;
    }


//    @Mapping(target = "merchantCategoryCodeId", expression = "java(terminal.getMcc() != null ? terminal.getMcc().getId() : null)")
//    @Mapping(target = "salesPointId", expression = "java(terminal.getSalesPoint() != null ? terminal.getSalesPoint().getId() : null)")
//    TerminalDto toDto(Terminal terminal);
}
