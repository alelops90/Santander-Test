package com.teste.bank.mapper;

import com.teste.bank.entity.BankTransaction;
import com.teste.bank.model.response.BankTransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankTransactionMapper {

    BankTransactionResponse toResponse(BankTransaction bankTransaction);
}
