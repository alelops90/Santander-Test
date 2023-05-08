package com.test.bank.mapper;

import com.test.bank.entity.BankClient;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.request.BankClientRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankClientMapper {

    BankClient toEntity(BankClientRequest bankClientRequest);

    BankClientResponse toResponse(BankClient bankClient);

}
