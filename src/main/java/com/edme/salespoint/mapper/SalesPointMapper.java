package com.edme.salespoint.mapper;


import com.edme.salespoint.dto.SalesPointDto;
import com.edme.salespoint.model.SalesPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AcquiringBankMapper.class})
public interface SalesPointMapper {
//    @Mapping(source = "acquiringBankId", target = "acquiringBank.id")
//    SalesPoint toEntity(SalesPointDto salesPointDto);
//
//    @Mapping(source = "acquiringBank.id", target = "acquiringBankId")
//    SalesPointDto toDto(SalesPoint salesPoint);


    SalesPointDto toDto(SalesPoint salesPoint);


    SalesPoint toEntity(SalesPointDto salesPointDto);

    default SalesPoint mapSalesPoint(Long id) {
        if(id == null) return null;
        SalesPoint salesPoint = new SalesPoint();
        salesPoint.setId(id);
        return salesPoint;
    }

}
