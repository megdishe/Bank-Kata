package com.kata.bank.domain;

import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Account {
    private final History history = new History();
    private final AtomicReference<BigDecimal> balance = new AtomicReference<>(BigDecimal.ZERO);

    public AtomicReference<BigDecimal> getBalance() {
        return balance;
    }

    public History getHistory() {
        return history;
    }


}
