package com.test.bank.mock;

import com.test.bank.entity.BankClient;
import com.test.bank.model.request.BankClientRequest;
import com.test.bank.model.request.BankTransactionRequest;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockForTest {
    private static final String DEPOSIT = "deposit";
    private static final String DRAWAL = "drawal";

    public static BankClientRequest createClientRequest() {
            return BankClientRequest.builder()
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();
    }

    public static BankClientResponse createClientResponse() {
        return BankClientResponse.builder()
                .id(4)
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();
    }

    public static BankClientRequest requestClient() {
        return BankClientRequest.builder()
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();
    }

    public static Page<BankClientResponse> pageClient() {
        List<BankClientResponse> bankClientResponseList = new ArrayList<>();
        BankClientResponse client01 = BankClientResponse.builder()
                .id(4)
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();

        BankClientResponse client02 = BankClientResponse.builder()
                .id(5)
                .name("Maria")
                .accountNumber("93120034")
                .balance(BigDecimal.valueOf(5000.00))
                .exclusive(false)
                .birthDate(new Date(1991, 05, 18))
                .build();
        BankClientResponse client03 = BankClientResponse.builder()
                .id(6)
                .name("Roberto")
                .accountNumber("93120035")
                .balance(BigDecimal.valueOf(750.00))
                .exclusive(false)
                .birthDate(new Date(1975, 06, 22))
                .build();
        BankClientResponse client04 = BankClientResponse.builder()
                .id(7)
                .name("Laura")
                .accountNumber("93120036")
                .balance(BigDecimal.valueOf(4500.00))
                .exclusive(true)
                .birthDate(new Date(1998, 03, 25))
                .build();
        bankClientResponseList.add(client01);
        bankClientResponseList.add(client02);
        bankClientResponseList.add(client03);
        bankClientResponseList.add(client04);
        return new PageImpl<>(bankClientResponseList);
    }

    public static List<BankClient> pageClientReturn() {
        List<BankClient> bankClientResponseList = new ArrayList<>();
        BankClient client01 = BankClient.builder()
                .id(4)
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();

        BankClient client02 = BankClient.builder()
                .id(5)
                .name("Maria")
                .accountNumber("93120034")
                .balance(BigDecimal.valueOf(5000.00))
                .exclusive(false)
                .birthDate(new Date(1991, 05, 18))
                .build();
        BankClient client03 = BankClient.builder()
                .id(6)
                .name("Roberto")
                .accountNumber("93120035")
                .balance(BigDecimal.valueOf(750.00))
                .exclusive(false)
                .birthDate(new Date(1975, 06, 22))
                .build();
        BankClient client04 = BankClient.builder()
                .id(7)
                .name("Laura")
                .accountNumber("93120036")
                .balance(BigDecimal.valueOf(4500.00))
                .exclusive(true)
                .birthDate(new Date(1998, 03, 25))
                .build();
        bankClientResponseList.add(client01);
        bankClientResponseList.add(client02);
        bankClientResponseList.add(client03);
        bankClientResponseList.add(client04);
        return bankClientResponseList;
    }

    public static BankClient clientReturn() {
        return BankClient.builder()
                .id(4)
                .name("João")
                .accountNumber("93120033")
                .balance(BigDecimal.valueOf(500.00))
                .exclusive(true)
                .birthDate(new Date(2001, 10, 15))
                .build();
    }

    public static BankTransactionRequest requestTransactionDeposit() {
        return BankTransactionRequest.builder()
                .amount(BigDecimal.valueOf(100.00))
                .name("João")
                .numberAccount("93120033")
                .operationType("deposit")
                .build();
    }

    public static BankTransactionRequest requestTransactionDrawal() {
        return BankTransactionRequest.builder()
                .amount(BigDecimal.valueOf(1000.00))
                .name("João")
                .numberAccount("93120033")
                .operationType("drawal")
                .build();
    }

    public static BankTransactionResponse responseTransactionDrawal() {
        return BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(100.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(400.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 04, 18, 10, 15))
                .operationType(DRAWAL)
                .build();
    }

    public static BankTransactionResponse responseTransactionDeposit() {
        return BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(100.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(600.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 04, 18, 10, 15))
                .operationType(DEPOSIT)
                .build();
    }

    public static Page<BankTransactionResponse> responseTransactionPage() {
        List<BankTransactionResponse> bankTransactionResponses = new ArrayList<>();
        BankTransactionResponse transaction01 = BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(100.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(600.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 02, 18, 10, 15))
                .operationType(DEPOSIT)
                .build();
        BankTransactionResponse transaction02 = BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(200.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(700.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 03, 18, 10, 15))
                .operationType(DEPOSIT)
                .build();

        BankTransactionResponse transaction03 = BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(300.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(800.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 03, 18, 10, 15))
                .operationType(DEPOSIT)
                .build();

        BankTransactionResponse transaction04 = BankTransactionResponse.builder()
                .id(12)
                .amount(BigDecimal.valueOf(400.00))
                .previousBalance(BigDecimal.valueOf(500.00))
                .newBalance(BigDecimal.valueOf(900.00))
                .numberAccount("93120033")
                .transactionTime(LocalDateTime.of(2023, 02, 18, 10, 15))
                .operationType(DEPOSIT)
                .build();
        bankTransactionResponses.add(transaction01);
        bankTransactionResponses.add(transaction02);
        bankTransactionResponses.add(transaction03);
        bankTransactionResponses.add(transaction04);

        return new PageImpl<>(bankTransactionResponses);
    }
}
