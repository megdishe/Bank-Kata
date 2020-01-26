package com.kata.bank.domain;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class History {
    private final Set<Transaction> transactions = new TreeSet<>(Comparator.comparing(Transaction::getDate));

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(transactions, history.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }

    @Override
    public String toString() {
        return "History{" +
                "transactions=" + transactions +
                '}';
    }
}
