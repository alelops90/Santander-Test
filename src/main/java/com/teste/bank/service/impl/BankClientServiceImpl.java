package com.teste.bank.service.impl;

import com.teste.bank.entity.BankClient;
import com.teste.bank.entity.BankTransaction;
import com.teste.bank.mapper.BankClientMapper;
import com.teste.bank.mapper.BankTransactionMapper;
import com.teste.bank.model.request.BankClientRequest;
import com.teste.bank.model.request.BankTransactionRequest;
import com.teste.bank.model.response.BankClientResponse;
import com.teste.bank.model.response.BankTransactionResponse;
import com.teste.bank.repository.BankClientRepository;
import com.teste.bank.repository.BankTransactionRepository;
import com.teste.bank.service.BankClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BankClientServiceImpl implements BankClientService {

    @Autowired
    private BankClientRepository bankClientRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankClientMapper bankClientMapper;

    @Autowired
    private BankTransactionMapper bankTransactionMapper;

    public BankClientServiceImpl(BankClientRepository bankClientRepository, BankTransactionRepository bankTransactionRepository,
                                 BankClientMapper bankClientMapper, BankTransactionMapper bankTransactionMapper) {
        this.bankClientRepository = bankClientRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankClientMapper = bankClientMapper;
        this.bankTransactionMapper = bankTransactionMapper;
    }

    @Override
    public BankClientResponse newClient(BankClientRequest bankClientRequest) {
        BankClient bankClient = bankClientMapper.toEntity(bankClientRequest);
        return bankClientMapper.toResponse(bankClientRepository.save(bankClient));
    }

    @Override
    public Page<BankClientResponse> findAll() {
        List<BankClient> bankClients = bankClientRepository.findAll();
        List<BankClientResponse> bankClientResponses = bankClients.stream().map(client -> bankClientMapper.toResponse(client)).collect(Collectors.toList());
        return new PageImpl<>(bankClientResponses);
    }

    @Override
    public BankTransactionResponse transactionBankDeposit(BankTransactionRequest bankTransactionRequest) {
        BankClient bankClient = bankClientRepository.findByName(bankTransactionRequest.getName());
        BankTransaction bankTransaction = BankTransaction.builder()
                .amount(bankTransactionRequest.getAmount())
                .numberAccount(bankTransactionRequest.getAccount())
                .previousbalance(bankClient.getBalance())
                .newbalance(updateBalance(bankClient.getBalance(), bankTransactionRequest.getAmount()))
                .transactionTIme(LocalDateTime.now()).build();
        return bankTransactionMapper.toResponse(bankTransactionRepository.save(bankTransaction));
    }

    @Override
    public BankTransactionResponse transactionBankDrawal(BankTransactionRequest bankTransactionRequest) {
        BankClient bankClient = bankClientRepository.findByName(bankTransactionRequest.getName());
        BigDecimal newBalance = new BigDecimal(BigInteger.ZERO);
        if (!bankClient.getExclusive()){
            if (bankTransactionRequest.getAmount().compareTo(BigDecimal.valueOf(100)) <= -1){
                newBalance = bankTransactionRequest.getAmount().subtract(bankClient.getBalance());
            } else if (BigDecimal.valueOf(100).compareTo(bankTransactionRequest.getAmount()) <= 0 && bankTransactionRequest.getAmount().compareTo(BigDecimal.valueOf(300)) <= 0) {
                newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount());
                newBalance.subtract(bankTransactionRequest.getAmount().multiply(BigDecimal.valueOf(0.4)));
            } else if (BigDecimal.valueOf(300).compareTo(bankTransactionRequest.getAmount()) < 0) {
                newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount());
                newBalance.subtract(bankTransactionRequest.getAmount().multiply(BigDecimal.valueOf(1)));
            }
        }
        BankTransaction bankTransaction = BankTransaction.builder().previousbalance(bankClient.getBalance())
                .newbalance(newBalance)
                .numberAccount(bankClient.getAccountNumber())
                .transactionTIme(LocalDateTime.now())
                .build();
        bankTransactionRepository.save(bankTransaction);
        return bankTransactionMapper.toResponse(bankTransaction);
    }

    @Override
    public Page<BankTransactionResponse> getTransaction() {
        List<BankTransaction> bankTransaction = bankTransactionRepository.findAll();
        List<BankTransactionResponse> bankTransactionResponses = bankTransaction.stream().map(transaction ->  bankTransactionMapper.toResponse(transaction)).collect(Collectors.toList());
        return new PageImpl<>(bankTransactionResponses);
    }

    private BigDecimal updateBalance(BigDecimal balance, BigDecimal amount) {
        return balance.add(amount);
    }
}
