package com.teste.bank.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_transaction")
@Data
@Builder
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "previous_balance")
    private BigDecimal previousbalance;

    @Column(name = "new_balance")
    private BigDecimal newbalance;

    @Column(name = "number_account")
    private String numberAccount;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTIme;

}
