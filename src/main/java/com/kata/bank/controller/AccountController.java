package com.kata.bank.controller;

import com.kata.bank.domain.History;
import com.kata.bank.domain.Transaction;
import com.kata.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@RestController
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("deposit")
    public ResponseEntity<Transaction> makeDeposit(@RequestParam("amount")
                                                   @Positive(message = "Amount must be positive")
                                                   @DecimalMax(value = "1000.00", message = "Amount should not be greater than 1000€")
                                                           BigDecimal amount) {
        Transaction transaction = accountService.makeDeposit(amount);
        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    @PostMapping("withdrawal")
    public ResponseEntity<Transaction> makeWithdrawal(@RequestParam("amount")
                                                      @Positive(message = "Amount must be positive")
                                                      @DecimalMax(value = "1000.00", message = "Amount should not be greater than 1000€")
                                                              BigDecimal amount) {
        Transaction transaction = accountService.makeWithdrawal(amount);
        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    @GetMapping("viewHistory")
    public ResponseEntity<History> viewHistory() {
        History history = accountService.viewHistory();
        return new ResponseEntity(history, HttpStatus.OK);
    }

}
