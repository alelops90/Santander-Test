package com.test.bank.service.impl;

import com.test.bank.entity.BankClient;
import com.test.bank.entity.BankTransaction;
import com.test.bank.exception.BusinessException;
import com.test.bank.exception.NotFoundException;
import com.test.bank.model.request.BankTransactionRequest;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import com.test.bank.repository.BankClientRepository;
import com.test.bank.repository.BankTransactionRepository;
import com.test.bank.service.BankClientService;
import com.test.bank.mapper.BankClientMapper;
import com.test.bank.mapper.BankTransactionMapper;
import com.test.bank.model.request.BankClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BankClientServiceImpl implements BankClientService {

    private static final String DRAWAL = "drawal";
    private static final String DEPOSIT = "deposit";

    @Autowired
    private BankClientRepository bankClientRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankClientMapper bankClientMapper;

    @Autowired
    private BankTransactionMapper bankTransactionMapper;

    @Override
    public BankClientResponse newClient(BankClientRequest bankClientRequest) {
        log.info("Create the new client " + bankClientRequest.getName());
        BankClient bankClient = bankClientMapper.toEntity(bankClientRequest);
        return bankClientMapper.toResponse(bankClientRepository.save(bankClient));
    }

    @Override
    public Page<BankClientResponse> findAll() {
        log.info("Find all clients in Data base");
        List<BankClient> bankClients = bankClientRepository.findAll();
        if (bankClients.isEmpty()) {
            log.error("There are no customers in the database");
            throw new NotFoundException("There are no customers in the database");
        }
        List<BankClientResponse> bankClientResponses = bankClients.stream().map(client -> bankClientMapper.toResponse(client)).collect(Collectors.toList());
        return new PageImpl<>(bankClientResponses);
    }

    @Override
    public BankTransactionResponse transactionBank(BankTransactionRequest bankTransactionRequest) {
        log.info("The client request one transaction the account number: " + bankTransactionRequest.getNumberAccount());
        BankClient bankClient = bankClientRepository.findByName(bankTransactionRequest.getName());
        BankTransaction bankTransaction;
        BigDecimal newBalance = new BigDecimal(BigInteger.ZERO);
        if (DEPOSIT.equalsIgnoreCase(bankTransactionRequest.getOperationType())) {
            log.info("Is Deposit transaction " + bankTransactionRequest.getName() + " the account number " + bankTransactionRequest.getNumberAccount());
            bankTransaction = bankTransactionMapper.toEntity(bankTransactionRequest);
            bankTransaction.setPreviousBalance(bankClient.getBalance());
            bankTransaction.setNewBalance(updateBalance(bankClient.getBalance(), bankTransactionRequest.getAmount()));
            bankTransaction.setTransactionTime(LocalDateTime.now());
            bankTransactionRepository.save(bankTransaction);
            bankClient.setBalance(bankTransaction.getNewBalance());
            bankClientRepository.save(bankClient);
        } else {
            log.info("Is drawal transaction " + bankTransactionRequest.getName() + "the account number " + bankTransactionRequest.getNumberAccount());
            hasBalance(bankTransactionRequest, bankClient);
            log.info("If has balance contnue this process");
            if (!bankClient.getExclusive()) {
                log.info("The client " + bankClient.getName() + " is not exclusive");
                if (bankTransactionRequest.getAmount().compareTo(BigDecimal.valueOf(100)) <= 0) {
                    newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount());
                } else if (BigDecimal.valueOf(100).compareTo(bankTransactionRequest.getAmount()) <= 0 && bankTransactionRequest.getAmount().compareTo(BigDecimal.valueOf(300)) <= 0) {
                    newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount())
                            .subtract(bankTransactionRequest.getAmount().multiply(BigDecimal.valueOf(0.004)));
                } else if (BigDecimal.valueOf(300).compareTo(bankTransactionRequest.getAmount()) < 0) {
                    newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount())
                            .subtract(bankTransactionRequest.getAmount().multiply(BigDecimal.valueOf(0.01)));
                }
            } else {
                log.info("The client is exclusive " + bankTransactionRequest.getName());
                newBalance = bankClient.getBalance().subtract(bankTransactionRequest.getAmount());
            }
            bankTransaction = bankTransactionMapper.toEntity(bankTransactionRequest);
            bankTransaction.setNewBalance(newBalance);
            bankTransaction.setTransactionTime(LocalDateTime.now());
            bankTransaction.setPreviousBalance(bankClient.getBalance());
            bankClient.setBalance(newBalance);
            bankClientRepository.save(bankClient);
            bankTransactionRepository.save(bankTransaction);
        }
        return bankTransactionMapper.toResponse(bankTransaction);
    }

    @Override
    public Page<BankTransactionResponse> getTransactionByDate(String numberAccount, String startDate, String endDate) {
        log.info("Get transaction in DataBase number account: " + numberAccount + " range date " + startDate + " " + endDate );
        List<BankTransaction> bankTransaction = bankTransactionRepository.findAllByDate(numberAccount, startDate, endDate);
        if (bankTransaction.isEmpty()){
            log.error("There are no transactions! ");
            throw new NotFoundException("There are no transactions! ");
        }
        List<BankTransactionResponse> bankTransactionResponses = bankTransaction.stream().map(transaction -> bankTransactionMapper.toResponse(transaction)).collect(Collectors.toList());
        return new PageImpl<>(bankTransactionResponses);
    }

    private BigDecimal updateBalance(BigDecimal balance, BigDecimal amount) {
        log.info("Update balance");
        return balance.add(amount);
    }

    private void hasBalance(BankTransactionRequest bankTransactionRequest, BankClient bankClient) {
        log.info("Check balance for transaction!");
        if (bankClient.getBalance().compareTo(bankTransactionRequest.getAmount()) <= -1) {
            throw new BusinessException("Your balance is less than the requested amount! ");
        }
    }
}
