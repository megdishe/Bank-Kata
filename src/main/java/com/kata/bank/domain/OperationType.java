package com.kata.bank.domain;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public enum OperationType {
    DEPOSIT(BigDecimal::add),
    WITHDRAWAL(BigDecimal::subtract);

    private BinaryOperator<BigDecimal> accumulatorFunction;

    OperationType(BinaryOperator<BigDecimal> accumulatorFunction) {
        this.accumulatorFunction = accumulatorFunction;
    }

    public BinaryOperator<BigDecimal> getAccumulatorFunction() {
        return accumulatorFunction;
    }
}
