package com.example.demo.mapper;

import com.example.demo.dto.TransactionDto;
import com.example.demo.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {TransactionType.class,
        Card.class,
        Terminal.class,
        ResponseCode.class,
        MerchantCategoryCodeMapper.class,
        SalesPointMapper.class,
        AcquiringBankMapper.class})
public interface TransactionMapper {
//    @Mapping(source = "transactionType.id", target = "transactionTypeId")
//    @Mapping(source = "card.id", target = "cardId")
//    @Mapping(source = "terminal.id", target = "terminalId")
//    @Mapping(source = "responseCode.id", target = "responseCodeId")
    TransactionDto toDto(Transaction transaction);

//    @Mapping(source = "transactionTypeId", target = "transactionType.id")
//    @Mapping(source = "cardId", target = "card.id")
//    @Mapping(source = "terminalId", target = "terminal.id")
//    @Mapping(source = "responseCodeId", target = "responseCode.id")
    Transaction toEntity(TransactionDto transactionDto);

    default TransactionType mapToTransactionType(Long id) {
        if (id == null) {
            return null;
        }
        TransactionType transactionType = new TransactionType();
        transactionType.setId(id);
        return transactionType;
    }

    default Card mapToCard(Long id) {
        if (id == null) {
            return null;
        }
        Card card = new Card();
        card.setId(id);
        return card;
    }

    default Terminal mapToTerminal(Long id) {
        if (id == null) {
            return null;
        }
        Terminal terminal = new Terminal();
        terminal.setId(id);
        return terminal;
    }

    default ResponseCode mapToResponseCode(Long id) {
        if (id == null) {
            return null;
        }
        ResponseCode responseCode = new ResponseCode();
        responseCode.setId(id);
        return responseCode;
    }
}
