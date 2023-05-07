package com.teste.bank.controller;

import com.teste.bank.model.request.BankTransactionRequest;
import com.teste.bank.model.request.BankClientRequest;
import com.teste.bank.model.response.BankClientResponse;
import com.teste.bank.model.response.BankTransactionResponse;
import com.teste.bank.service.BankClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankClientService bankClientService;

    @PostMapping("/new-client")
    public ResponseEntity<BankClientResponse> newClient(BankClientRequest bankClientRequest){
        BankClientResponse bankClientResponse = bankClientService.newClient(bankClientRequest);
        return ResponseEntity.ok(bankClientResponse);
    }

    @GetMapping("/all-client")
    public ResponseEntity<Page<BankClientResponse>> findAll(){
        Page<BankClientResponse> bankClientResponsePage = bankClientService.findAll();
        return ResponseEntity.ok(bankClientResponsePage);
    }

    @PostMapping("/transaction")
    public ResponseEntity<BankTransactionResponse> transactionBankDeposit(@RequestBody BankTransactionRequest bankTransactionRequest) {
        BankTransactionResponse bankTransactionResponse = bankClientService.transactionBankDeposit(bankTransactionRequest);
        return ResponseEntity.ok(bankTransactionResponse);
    }

    @PostMapping("/transaction")
    public ResponseEntity<BankTransactionResponse> transactionBankDrawal(@RequestBody BankTransactionRequest bankTransactionRequest) {
        BankTransactionResponse bankTransactionResponse = bankClientService.transactionBankDrawal(bankTransactionRequest);
        return ResponseEntity.ok(bankTransactionResponse);
    }

    @GetMapping("/transaction-consult")
    public ResponseEntity<Page<BankTransactionResponse>> transactionAll(){
        Page<BankTransactionResponse> bankTransactionResponse = bankClientService.getTransaction();
        return ResponseEntity.ok(bankTransactionResponse);
    }
}
