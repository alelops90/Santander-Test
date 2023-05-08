package com.test.bank.mapper;

import com.test.bank.entity.BankTransaction;
import com.test.bank.model.request.BankTransactionRequest;
import com.test.bank.model.response.BankTransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankTransactionMapper {

    BankTransactionResponse toResponse(BankTransaction bankTransaction);

    BankTransaction toEntity(BankTransactionRequest bankTransactionRequest);
}
