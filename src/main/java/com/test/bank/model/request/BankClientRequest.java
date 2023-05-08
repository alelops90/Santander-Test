package com.test.bank.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class BankClientRequest {

    private Integer id;
    @NotBlank(message = "This field name is required")
    @Schema(description = "Field this name of customer",
            example = "John")
    private String name;
    @NotBlank(message = "This field name is required")
    @Schema(description = "Field exclusive is required",
            example = "true")
    private Boolean exclusive;
    @Schema(description = "Field available value reference ",
            example = "500.00")
    private BigDecimal balance;
    @Schema(description = "This field is the account number ",
            example = "123456")
    private String accountNumber;
    @NotBlank(message = "This field name is required")
    @Schema(description = "This field is the birth date ",
            example = "1990-03-11")
    private Date birthDate;
}
