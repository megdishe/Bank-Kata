package com.kata.bank.domain;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private final History history = new History();
    @DecimalMin(value = "-350.00", message = "Balance should not be less than -350€")
    @DecimalMax(value = "20000.00", message = "Balance should not be greater than 20000€")
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public History getHistory() {
        return history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(balance, account.balance) &&
                Objects.equals(history, account.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, history);
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", history=" + history +
                '}';
    }
}
