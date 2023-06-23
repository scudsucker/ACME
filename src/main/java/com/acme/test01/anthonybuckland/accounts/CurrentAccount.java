package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.database.Database;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.AccountNotFoundException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.anthonybuckland.models.Account;
import com.acme.test01.anthonybuckland.models.AccountType;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;

public class CurrentAccount extends AbstractCurrentAccount {

    private static final long MINIMUM_BALANCE = -10000000L;
    private final Database db;

    public CurrentAccount(Database db) {
        this.db = db;
    }

    public void openCurrentAccount(Long accountId, Long amountToDeposit) {
        if (db.getUserAccountById(accountId) != null) {
            System.out.println("Open acc " + (amountToDeposit / 100));
            throw new AccountAlreadyExistsException(CANNOT_OPEN_ACCOUNT_WHEN_ACCOUNT_ID_EXISTS.label);
        }
        if (amountToDeposit < 0) {
            throw new DepositAmountTooSmallException(CANNOT_OPEN_CURRENT_UNDER_ZERO.label);
        }
        this.db.create(accountId, new Account(AccountType.CURRENT, amountToDeposit));
    }

    public void openCurrentAccount(Long accountId) {
        openCurrentAccount(accountId, 0L);
    }

    public void withdraw(Long accountId, int amountToWithdraw) throws WithdrawalAmountTooLargeException {
        Account account = db.getUserAccountById(accountId);
        if (account.getBalance() < (amountToWithdraw + MINIMUM_BALANCE)) {
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

