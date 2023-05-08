package com.teste.bank.model.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankClientRequest {

    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Boolean exclusive;
    private BigDecimal balance;
    private String accountNumber;
    @NonNull
    private Date birthDate;
}
