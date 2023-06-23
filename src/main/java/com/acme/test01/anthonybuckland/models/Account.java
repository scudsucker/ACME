package com.acme.test01.anthonybuckland.models;

import java.util.Objects;

public class Account {

    private Enum<AccountType> accountType;

    private Long balance;

    private Long overdraftLimit = 0L;

    public Account(Enum<AccountType> accountType, Long balance, Long overdraftLimit) {
        this.accountType = accountType;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
    }
    public Account(Enum<AccountType> accountType, Long balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public Enum<AccountType> getAccountType() {
        return accountType;
    }

    public void setAccountType(Enum<AccountType> accountType) {
        this.accountType = accountType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(Long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
