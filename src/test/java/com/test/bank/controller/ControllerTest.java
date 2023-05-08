package com.test.bank.controller;

import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import com.test.bank.service.BankClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static com.test.bank.mock.MockForTest.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test of controller")
public class ControllerTest {

    @InjectMocks
    private BankController bankController;

    @Mock
    private BankClientService bankClientServiceMock;

    @Test
    @DisplayName("Create a new customer in DataBase")
    void newClientSaveWithSucces() {

        BDDMockito.when(bankClientServiceMock.newClient(createClientRequest()))
                .thenReturn(createClientResponse());

        BankClientResponse body = bankController.newClient(requestClient()).getBody();
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getId()).isEqualTo(4);
        Assertions.assertThat(body.getName()).isEqualTo("João");
        Assertions.assertThat(body.getExclusive()).isEqualTo(true);
        Assertions.assertThat(body.getBalance()).isEqualTo(BigDecimal.valueOf(500.00));
        Assertions.assertThat(body.getAccountNumber()).isEqualTo("93120033");
        Assertions.assertThat(body.getBirthDate()).isEqualTo(new Date(2001,10,15));
    }

    @Test
    @DisplayName("Get customer in DataBase")
    void getClientSaveWithSucces() {

        BDDMockito.when(bankClientServiceMock.findAll())
                .thenReturn(pageClient());

        Page<BankClientResponse> page = bankController.findAll().getBody();
        Assertions.assertThat(page.getTotalElements()).isEqualTo(4);
        Assertions.assertThat(page.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("Transaction With Success")
    void transactionWithSucces() {

        BDDMockito.when(bankClientServiceMock.transactionBank(requestTransactionDeposit()))
                .thenReturn(responseTransactionDeposit());

        BankTransactionResponse responseTransaction = bankController.transactionBank(requestTransactionDeposit()).getBody();
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(12);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(500.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(600.0));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getTransactionTime()).isEqualTo(LocalDateTime.of(2023,04,18,10,15));
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("deposit");

    }

    @Test
    @DisplayName("Transaction With Success")
    void transactionWithSuccesDrawal() {

        BDDMockito.when(bankClientServiceMock.transactionBank(requestTransactionDrawal()))
                .thenReturn(responseTransactionDrawal());

        BankTransactionResponse responseTransaction = bankController.transactionBank(requestTransactionDrawal()).getBody();
        Assertions.assertThat(responseTransaction.getId()).isEqualTo(12);
        Assertions.assertThat(responseTransaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        Assertions.assertThat(responseTransaction.getPreviousBalance()).isEqualTo(BigDecimal.valueOf(500.0));
        Assertions.assertThat(responseTransaction.getNewBalance()).isEqualTo(BigDecimal.valueOf(400.0));
        Assertions.assertThat(responseTransaction.getNumberAccount()).isEqualTo("93120033");
        Assertions.assertThat(responseTransaction.getTransactionTime()).isEqualTo(LocalDateTime.of(2023,04,18,10,15));
        Assertions.assertThat(responseTransaction.getOperationType()).isEqualTo("drawal");

    }

    @Test
    @DisplayName("Transaction Without Success")
    void transactionByDate() {

        BDDMockito.when(bankClientServiceMock.getTransactionByDate(Mockito.any(),Mockito.any(),Mockito.any()))
                .thenReturn(responseTransactionPage());

        Page<BankTransactionResponse> transactionResponses = bankController.transactionByDate("93120033", "2023-02-01", "2023-05-08").getBody();
        Assertions.assertThat(transactionResponses.getTotalElements()).isEqualTo(4);
        Assertions.assertThat(transactionResponses.getTotalPages()).isEqualTo(1);
    }
}
