package com.teste.bank.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransactionRequest {

    private BigDecimal amount;
    private String name;
    private String numberAccount;
    private String operationType;
}
