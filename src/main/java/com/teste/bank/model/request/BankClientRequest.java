package com.teste.bank.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankClientRequest {

    private Integer id;
    private String name;
    private Boolean exclusive;
    private BigDecimal balance;
    private String accountNumber;
    private Date birthDate;
}
