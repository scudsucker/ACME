package com.acme.test01.anthonybuckland.accounts.utility;

import com.acme.test01.anthonybuckland.database.Database;
import com.acme.test01.anthonybuckland.database.SystemDB;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.AccountNotFoundException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.anthonybuckland.models.Account;
import com.acme.test01.anthonybuckland.models.AccountType;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;
import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.ACCOUNT_NOT_FOUND;

public class CommonAccountUtility {

    private final Database db = SystemDB.getInstance();

    public void withdraw(Long accountId, int amountToWithdraw, long intMinimumBalance) throws WithdrawalAmountTooLargeException {
        Account account = db.getUserAccountById(accountId);
        if (account.getBalance() < (amountToWithdraw + intMinimumBalance)) {
            throw new WithdrawalAmountTooLargeException(CANNOT_WITHDRAW_MORE_THAN_THE_LIMIT.label);
        }
        account.setBalance(account.getBalance() - amountToWithdraw);
        db.update(accountId, account);
    }

    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        Account account = db.getUserAccountById(accountId);
        if (account == null) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND.label);
        }
        account.setBalance(account.getBalance() + amountToDeposit);
        db.update(accountId, account);
    }

    public Long getAccountBalance(Long accountId) {
        return db.getUserAccountById(accountId).getBalance();
    }

}
