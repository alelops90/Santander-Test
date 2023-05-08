package com.test.bank.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "This field is the requested amount ",
            example = "100.00")
    private BigDecimal amount;
    @Schema(description = "This field is the customer name ",
            example = "Maria")
    private String name;
    @Schema(description = "This field is the account number ",
            example = "93120031")
    private String numberAccount;
    @Schema(description = "This field is the operation type ",
            example = "deposit")
    private String operationType;
}
