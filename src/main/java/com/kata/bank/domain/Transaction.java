package com.kata.bank.domain;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private final OperationType operationType;
    private final LocalDateTime date;
    private final BigDecimal amount;
    @DecimalMin(value = "-350.00", message = "Balance should not be less than -350€")
    @DecimalMax(value = "20000.00", message = "Balance should not be greater than 20000€")
    private final BigDecimal balance;

    public Transaction(OperationType operationType, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
        this.operationType = operationType;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public OperationType getOperationType() {
        return operationType;
    }


    public LocalDateTime getDate() {
        return date;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public BigDecimal getBalance() {
        return balance;
    }


    @Override
    public String toString() {
        return "(" + operationType + ", " + date + ", " + amount + ", " + balance + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return operationType == that.operationType &&
                Objects.equals(date, that.date) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, date, amount, balance);
    }
}
