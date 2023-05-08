package com.test.bank.service;

import com.test.bank.entity.BankClient;
import com.test.bank.entity.BankTransaction;
import com.test.bank.exception.BusinessException;
import com.test.bank.exception.NotFoundException;
import com.test.bank.mapper.BankClientMapper;
import com.test.bank.mapper.BankTransactionMapper;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import com.test.bank.repository.BankClientRepository;
import com.test.bank.repository.BankTransactionRepository;
import com.test.bank.service.impl.BankClientServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static com.test.bank.mock.MockForTest.*;
import static com.test.bank.mock.MockForTest.responseTransactionPage;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@DisplayName("Test of Service")
public class BankClientServiceTest {

    @InjectMocks
    private BankClientServiceImpl bankClientService;

    @Mock
    private BankClientMapper bankClientMapper;

    @Mock
    private BankTransactionMapper bankTransactionMapper;

    @Mock
    private BankClientRepository bankClientRepository;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Mock
    private BankTransaction bankTransaction;

    @Test
    @DisplayName("Create a new customer in DataBase")
    void newClientSaveWithSucces() {

        BDDMockito.when(bankClientService.newClient(createClientRequest()))
                .thenReturn(createClientResponse());

        BankClientResponse body = bankClientService.newClient(requestClient());
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getId()).isEqualTo(4);
        Assertions.assertThat(body.getName()).isEqualTo("João");
        Assertions.assertThat(body.getExclusive()).isEqualTo(true);
        Assertions.assertThat(body.getBalance()).isEqualTo(BigDecimal.valueOf(500.00));
        Assertions.assertThat(body.getAccountNumber()).isEqualTo("93120033");
        Assertions.assertThat(body.getBirthDate()).isEqualTo(new Date(2001, 10, 15));
    }

    @Test
    @DisplayName("Get customer in DataBase")
    void getClientSaveWithSucces() {

        BDDMockito.when(bankClientRepository.findAll())
                .thenReturn(pageClientReturn());

        Page<BankClientResponse> page = bankClientService.findAll();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(4);
        Assertions.assertThat(page.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("Get customer in DataBase With error")
    void getClientSaveWithoutSucces() {

        BDDMockito.when(bankClientRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> bankClientService.findAll());

    }

    @Test
    @DisplayName("Transaction With Success")
    void transactionWithSucces() {

        BDDMockito.when(bankClientRepository.findByName(Mockito.any()))
                .thenReturn(BankClient.builder()
                        .id(4)
                        .name("João")
                        .accountNumber("93120033")
                        .balance(BigDecimal.valueOf(500.0))
                        .exclusive(true)
                        .birthDate(new Date(2001, 10, 15))
                        .build());

        BDDMockito.when(bankTransactionMapper.toEntity(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .operationType("Deposit")
                        .numberAccount("93120031")
                        .build());

        BDDMockito.when(bankTransactionRepository.save(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(600.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("deposit").build());
        BDDMockito.when(bankTransactionMapper.toResponse(Mockito.any()))
                .thenReturn(BankTransactionResponse.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(600.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("deposit")
                        .build());
        BankTransactionResponse responseTransaction = bankClientService.transactionBank(requestTransactionDeposit());
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(1);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(500.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(600.0));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("deposit");

    }

    @Test
    @DisplayName("Transaction With Success")
    void transactionWithSuccesDrawal() {

        BDDMockito.when(bankClientRepository.findByName(Mockito.any()))
                .thenReturn(BankClient.builder()
                        .id(4)
                        .name("João")
                        .accountNumber("93120033")
                        .balance(BigDecimal.valueOf(500.0))
                        .exclusive(true)
                        .birthDate(new Date(2001, 10, 15))
                        .build());

        BDDMockito.when(bankTransactionMapper.toEntity(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .operationType("drawal")
                        .numberAccount("93120031")
                        .build());

        BDDMockito.when(bankTransactionRepository.save(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(400.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal").build());

        BDDMockito.when(bankTransactionMapper.toResponse(Mockito.any()))
                .thenReturn(BankTransactionResponse.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(400.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal")
                        .build());

        BankTransactionResponse responseTransaction = bankClientService.transactionBank(requestTransactionDrawalService());
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(1);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(500.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(400.0));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("drawal");

    }

    @Test
    @DisplayName("Transaction With Success and rate")
    void transactionWithSuccesDrawalAndRate() {

        BDDMockito.when(bankClientRepository.findByName(Mockito.any()))
                .thenReturn(BankClient.builder()
                        .id(4)
                        .name("João")
                        .accountNumber("93120033")
                        .balance(BigDecimal.valueOf(500.0))
                        .exclusive(false)
                        .birthDate(new Date(2001, 10, 15))
                        .build());

        BDDMockito.when(bankTransactionMapper.toEntity(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .amount(BigDecimal.valueOf(101.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .operationType("drawal")
                        .numberAccount("93120031")
                        .build());

        BDDMockito.when(bankTransactionRepository.save(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(101.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(398.6))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal").build());

        BDDMockito.when(bankTransactionMapper.toResponse(Mockito.any()))
                .thenReturn(BankTransactionResponse.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(101.0))
                        .previousBalance(BigDecimal.valueOf(500.0))
                        .newBalance(BigDecimal.valueOf(398.6))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal")
                        .build());

        BankTransactionResponse responseTransaction = bankClientService.transactionBank(requestTransactionDrawalServiceAndRate());
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(1);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(101.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(500.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(398.6));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("drawal");

    }

    @Test
    @DisplayName("Transaction With Success and rate")
    void transactionWithSuccesDrawalAndRate2() {

        BDDMockito.when(bankClientRepository.findByName(Mockito.any()))
                .thenReturn(BankClient.builder()
                        .id(4)
                        .name("João")
                        .accountNumber("93120033")
                        .balance(BigDecimal.valueOf(5000.0))
                        .exclusive(false)
                        .birthDate(new Date(2001, 10, 15))
                        .build());

        BDDMockito.when(bankTransactionMapper.toEntity(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .amount(BigDecimal.valueOf(1000.0))
                        .previousBalance(BigDecimal.valueOf(5000.0))
                        .operationType("drawal")
                        .numberAccount("93120031")
                        .build());

        BDDMockito.when(bankTransactionRepository.save(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(1000.0))
                        .previousBalance(BigDecimal.valueOf(5000.0))
                        .newBalance(BigDecimal.valueOf(3990.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal").build());

        BDDMockito.when(bankTransactionMapper.toResponse(Mockito.any()))
                .thenReturn(BankTransactionResponse.builder()
                        .id(1)
                        .amount(BigDecimal.valueOf(1000.0))
                        .previousBalance(BigDecimal.valueOf(5000.0))
                        .newBalance(BigDecimal.valueOf(3990.0))
                        .numberAccount("93120033")
                        .transactionTime(LocalDateTime.now())
                        .operationType("drawal")
                        .build());

        BankTransactionResponse responseTransaction = bankClientService.transactionBank(requestTransactionDrawalServiceAndRate2());
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(1);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(1000.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(5000.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(3990.0));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("drawal");

    }

    @Test
    @DisplayName("Transaction With Success and rate")
    void transactionWithoutSuccess() {

        BDDMockito.when(bankClientRepository.findByName(Mockito.any()))
                .thenReturn(BankClient.builder()
                        .id(4)
                        .name("João")
                        .accountNumber("93120033")
                        .balance(BigDecimal.valueOf(100.0))
                        .exclusive(false)
                        .birthDate(new Date(2001, 10, 15))
                        .build());

        BDDMockito.when(bankTransactionMapper.toEntity(Mockito.any()))
                .thenReturn(BankTransaction.builder()
                        .amount(BigDecimal.valueOf(100.0))
                        .previousBalance(BigDecimal.valueOf(100.0))
                        .operationType("drawal")
                        .numberAccount("93120031")
                        .build());

        assertThatExceptionOfType(BusinessException.class).isThrownBy(() -> bankClientService.transactionBank(requestTransactionDrawalServiceAndRate()));
    }

    @Test
    @DisplayName("Get Transaction in DataBase With error")
    void getTransactionSaveWithoutSucces() {

        BDDMockito.when(bankTransactionRepository.findAllByDate(Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(Collections.emptyList());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> bankClientService.getTransactionByDate(Mockito.any(),Mockito.any(),Mockito.any()));

    }

    @Test
    @DisplayName("Get transaction in DataBase")
    void getTransactionSaveWithSucces() {

        BDDMockito.when( bankTransactionRepository.findAllByDate(Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(responseTransactionInDatabase());

        Page<BankTransactionResponse> page = bankClientService.getTransactionByDate(Mockito.any(),Mockito.any(),Mockito.any());
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(page.getTotalPages()).isEqualTo(1);
    }
}
