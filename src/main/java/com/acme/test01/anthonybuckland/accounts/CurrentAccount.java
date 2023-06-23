package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.exceptions.AccountNotFoundException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;

public class CurrentAccount extends AbstractCurrentAccount {
    public void openCurrentAccount(Long accountId) {};
    public void withdraw(Long accountId, int amountToWithdraw)
            throws AccountNotFoundException, WithdrawalAmountTooLargeException {};
    public void deposit(Long accountId, int amountToDeposit)throws
            AccountNotFoundException {};
}

