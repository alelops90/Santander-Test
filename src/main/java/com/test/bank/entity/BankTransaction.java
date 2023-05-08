package com.test.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_transaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "previous_balance")
    private BigDecimal previousBalance;

    @Column(name = "new_balance")
    private BigDecimal newBalance;

    @Column(name = "number_account")
        private String numberAccount;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @Column(name = "operation_type")
    private String operationType;

}
