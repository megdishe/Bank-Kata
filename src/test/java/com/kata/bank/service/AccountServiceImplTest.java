package com.kata.bank.service;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.History;
import com.kata.bank.domain.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {

    @Mock
    private Account account;
    @Mock
    private LocalValidatorFactoryBean validator;
    @InjectMocks
    private AccountServiceImpl accountService;

    private ExecutorService executor;


    @Before
    public void setUp() throws Exception {
        Mockito.when(account.getBalance())
                .thenReturn(new AtomicReference<>(BigDecimal.ZERO));
        Mockito.when(account.getHistory())
                .thenReturn(new History());

        executor = Executors.newFixedThreadPool(10);
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdownNow();
    }

    @Test
    public void makeDeposit() throws InterruptedException {
        List<Callable<Transaction>> callableList = new ArrayList<>();
        IntStream.rangeClosed(100, 5000)
                .filter(amount -> amount % 100 == 0)
                .forEach(amount -> {
                    Callable<Transaction> deposit = () -> accountService.makeDeposit(BigDecimal.valueOf(amount));
                    callableList.add(deposit);
                });
        List<Transaction> transactionList = executor.invokeAll(callableList).stream()
                .map(future -> getTransaction(future))
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
        BigDecimal balance = transactionList.get(transactionList.size() - 1).getBalance();

        BigDecimal sumOfAmounts = transactionList.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Assert.assertEquals(balance, sumOfAmounts);
    }

    private Transaction getTransaction(Future<Transaction> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
