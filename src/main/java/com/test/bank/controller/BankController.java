package com.test.bank.controller;

import com.test.bank.exception.BusinessException;
import com.test.bank.exception.NotFoundException;
import com.test.bank.model.request.BankClientRequest;
import com.test.bank.model.request.BankTransactionRequest;
import com.test.bank.model.response.BankClientResponse;
import com.test.bank.model.response.BankTransactionResponse;
import com.test.bank.service.BankClientService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankClientService bankClientService;

    @PostMapping("/new-client")
    @Operation(summary = "Create new customer", description = "This method create a new customer")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
                           @ApiResponse(code = 400, message = "Bad Request", response = BusinessException.class)})
    public ResponseEntity<BankClientResponse> newClient(@RequestBody @Valid BankClientRequest bankClientRequest) {
        BankClientResponse bankClientResponse = bankClientService.newClient(bankClientRequest);
        return new ResponseEntity<>(bankClientResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all-client")
    @Operation(summary = "Get all customers from Data Base", description = "This method return all customer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
                           @ApiResponse(code = 400, message = "Not Found", response = NotFoundException.class)})
    public ResponseEntity<Page<BankClientResponse>> findAll() {
        Page<BankClientResponse> bankClientResponsePage = bankClientService.findAll();
        return ResponseEntity.ok(bankClientResponsePage);
    }

    @PostMapping("/transaction")
    @Operation(summary = "Create a new transaction ", description = "Create a new Withdrawal or deposit.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
                           @ApiResponse(code = 400, message = "Bad Request", response = BusinessException.class)})
    public ResponseEntity<BankTransactionResponse> transactionBank(@RequestBody BankTransactionRequest bankTransactionRequest) {
        BankTransactionResponse bankTransactionResponse = bankClientService.transactionBank(bankTransactionRequest);
        return ResponseEntity.ok().body(bankTransactionResponse);
    }

    @GetMapping("/transaction-consult/{numberAccount}/{startDate}_{endDate}")
    @Operation(summary = "Find transaction in Data Base", description = "Find transaction in Data base  with a start date and end date basead on reported account .")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
                           @ApiResponse(code = 400, message = "Bad Rquest")})
    public ResponseEntity<Page<BankTransactionResponse>> transactionByDate(@PathVariable @Valid String numberAccount,
                                                                           @PathVariable @Valid String startDate,
                                                                           @PathVariable @Valid String endDate) {
        Page<BankTransactionResponse> bankTransactionResponse = bankClientService.getTransactionByDate(numberAccount, startDate, endDate);
        return ResponseEntity.ok(bankTransactionResponse);
    }
}
