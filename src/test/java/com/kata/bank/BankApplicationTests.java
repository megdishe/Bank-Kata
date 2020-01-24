package com.kata.bank;

import com.kata.bank.domain.Account;
import com.kata.bank.domain.Transaction;
import com.kata.bank.service.AccountService;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankApplicationTests {
    @Autowired
    private Account account;
    @Autowired
    private AccountService accountService;

    @Rule
    public ExpectedException expectedException= ExpectedException.none();;

    @After
    public void tearDown(){
        account.setBalance(BigDecimal.ZERO);
    }

    @Test
    public void testOrdinaryBehavior() {
        BigDecimal amount = BigDecimal.valueOf(175);
        Transaction depositTransaction = accountService.makeDeposit(amount);
        Transaction withdrawalTransaction = accountService.makeWithdrawal(amount);
        Set<Transaction> transactions = accountService.viewHistory().getTransactions();
        Assert.assertTrue(depositTransaction.getAmount().equals(amount));
        Assert.assertTrue(withdrawalTransaction.getAmount().equals(amount));
        Assert.assertEquals(depositTransaction, ((TreeSet) transactions).first());
        Assert.assertEquals(withdrawalTransaction, ((TreeSet) transactions).last());
        Assert.assertTrue(transactions.size() == 2);
        Assert.assertTrue(account.getBalance().equals(BigDecimal.ZERO));
    }

    @Test
    public void testDepositBoundaryValue(){
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage("Balance should not be greater than 20000€");
        BigDecimal amount = BigDecimal.valueOf(20000.55);
        accountService.makeDeposit(amount);
    }

    @Test
    public void testWithdrawalBoundaryValue(){
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expectMessage("Balance should not be less than -350€");
        BigDecimal amount = BigDecimal.valueOf(351);
        Transaction withdrawalTransaction = accountService.makeWithdrawal(amount);
    }


}
