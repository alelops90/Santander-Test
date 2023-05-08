package com.test.bank.service;

import com.test.bank.model.request.BankTransactionRequest;
import com.test.bank.model.request.BankClientRequest;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BankClientService {

    BankClientResponse newClient(BankClientRequest bankClientRequest);

    Page<BankClientResponse> findAll();

    BankTransactionResponse transactionBank(BankTransactionRequest bankTransactionRequest);

    Page<BankTransactionResponse> getTransactionByDate(String numberAccount, String startDate, String endDate);
}
