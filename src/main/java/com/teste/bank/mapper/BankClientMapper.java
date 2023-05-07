package com.teste.bank.mapper;

import com.teste.bank.entity.BankClient;
import com.teste.bank.model.request.BankClientRequest;
import com.teste.bank.model.response.BankClientResponse;
import com.teste.bank.model.response.BankTransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankClientMapper {

    BankClient toEntity(BankClientRequest bankClientRequest);

    BankClientResponse toResponse(BankClient bankClient);

}
