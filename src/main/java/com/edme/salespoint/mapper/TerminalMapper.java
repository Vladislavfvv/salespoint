package com.edme.salespoint.mapper;

import com.edme.salespoint.dto.TerminalDto;
import com.edme.salespoint.model.MerchantCategoryCode;
import com.edme.salespoint.model.SalesPoint;
import com.edme.salespoint.model.Terminal;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring" )
@Mapper(componentModel = "spring", uses = {
        MerchantCategoryCodeMapper.class,
        AcquiringBankMapper.class,
        SalesPointMapper.class})
public interface TerminalMapper {
    //    @Mapping(source = "mcc.id", target = "merchantCategoryCodeId")
//    @Mapping(source = "salesPoint.id", target = "salesPointId")
//    TerminalDto toDto(Terminal terminal);
//
//    @Mapping(source = "merchantCategoryCodeId", target = "mcc.id")
//    @Mapping(source = "salesPointId", target = "salesPoint.id")
//    Terminal toEntity(TerminalDto terminalDto);
//    @Mapping(source = "mcc", target = "mcc")
//    @Mapping(source = "salesPoint", target = "salesPoint")
    TerminalDto toDto(Terminal terminalId);

//    @Mapping(target = "mcc", expression = "java(mapMcc(terminalDto.getMerchantCategoryCodeId()))")
//    @Mapping(target = "salesPoint", expression = "java(mapSalesPoint(terminalDto.getSalesPointId()))")

    //    @Mapping(source = "mcc.id", target = "mcc.id")
//    @Mapping(source = "salesPoint.id", target = "salesPoint.id")
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
