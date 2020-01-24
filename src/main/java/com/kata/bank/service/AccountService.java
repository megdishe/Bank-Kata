package com.kata.bank.service;

import com.kata.bank.domain.History;
import com.kata.bank.domain.Transaction;

import java.math.BigDecimal;

public interface AccountService {

    Transaction makeDeposit(BigDecimal amount);

    Transaction makeWithdrawal(BigDecimal amount);

    History viewHistory();
}
