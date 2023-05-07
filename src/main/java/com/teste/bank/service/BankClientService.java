package com.teste.bank.service;

import com.teste.bank.model.request.BankClientRequest;
import com.teste.bank.model.request.BankTransactionRequest;
import com.teste.bank.model.response.BankClientResponse;
import com.teste.bank.model.response.BankTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BankClientService {

    BankClientResponse newClient(BankClientRequest bankClientRequest);

    Page<BankClientResponse> findAll();

    BankTransactionResponse transactionBankDeposit(BankTransactionRequest bankTransactionRequest);

    BankTransactionResponse transactionBankDrawal(BankTransactionRequest bankTransactionRequest);

    Page<BankTransactionResponse> getTransaction();
}
