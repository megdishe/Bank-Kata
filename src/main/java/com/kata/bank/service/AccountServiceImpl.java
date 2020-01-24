package com.kata.bank.service;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.History;
import com.kata.bank.domain.OperationType;
import com.kata.bank.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private Account account;
    @Autowired
    private Validator validator;

    @Override
    public Transaction makeDeposit(BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        BigDecimal updatedBalance = balance.add(amount);
        Transaction transaction = new Transaction(OperationType.DEPOSIT, LocalDateTime.now(), amount, updatedBalance);
        account.setBalance(updatedBalance);
        validateAccount();
        History history = account.getHistory();
        history.getTransactions().add(transaction);
        return transaction;
    }

    @Override
    public Transaction makeWithdrawal(BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        BigDecimal updatedBalance = balance.subtract(amount);
        Transaction transaction = new Transaction(OperationType.WITHDRAWAL, LocalDateTime.now(), amount, updatedBalance);
        account.setBalance(updatedBalance);
        validateAccount();
        History history = account.getHistory();
        history.getTransactions().add(transaction);
        return transaction;
    }

    private void validateAccount() {
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Override
    public History viewHistory() {
        return account.getHistory();
    }
}
