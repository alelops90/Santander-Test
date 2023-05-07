package com.teste.bank.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bank_client")
@Data
@Builder
public class BankClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "exclusive")
    private Boolean exclusive;

    @Column(name = "balance")
    private BigDecimal balance;

    @NonNull
    @Column(name = "account_number")
    private String accountNumber;

    @NonNull
    @Column(name = "birth_date")
    private Date birthDate;
}
