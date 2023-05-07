package com.teste.bank.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransactionResponse {

    private Integer id;
    private BigDecimal previousBalance;
    private BigDecimal updateBalance;
    private LocalDateTime transactionTime;
}
