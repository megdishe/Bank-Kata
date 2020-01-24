package com.kata.bank;

import com.kata.bank.domain.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public Account account() {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        return account;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
