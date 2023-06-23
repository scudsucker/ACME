package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.database.Database;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.AccountNotFoundException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.anthonybuckland.models.Account;
import com.acme.test01.anthonybuckland.models.AccountType;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;

public final class SavingsAccount extends AbstractSavingsAccount {
    private static final long minimumBalance = 200000L;
    private Database db;

    public SavingsAccount(Database db) {
        this.db = db;
    }

    public void openSavingsAccount(Long accountId, Long amountToDeposit) {
        if (amountToDeposit < minimumBalance) {
            System.out.println("Open acc " + (amountToDeposit / 100) + " vs " + (minimumBalance / 100));
            throw new DepositAmountTooSmallException(CANNOT_OPEN_SAVINGS_UNDER_2000.label);
        }
        if (db.getUserAccountById(accountId) != null ) {
            System.out.println("Open acc " + (amountToDeposit / 100) + " vs " + (minimumBalance / 100));
            throw new AccountAlreadyExistsException(CANNOT_OPEN_SAVINGS_ACCOUNT_WHEN_CUSTOMER_ID_EXISTS.label);
        }
        this.db.create(accountId, new Account(AccountType.SAVINGS, amountToDeposit));
    }

    public void withdraw(Long accountId, int amountToWithdraw) throws WithdrawalAmountTooLargeException {
        Account account = db.getUserAccountById(accountId);
        if (account.getBalance() < (amountToWithdraw + minimumBalance)) {
            throw new WithdrawalAmountTooLargeException(CANNOT_OPEN_SAVINGS_UNDER_2000.label);
        }
        account.setBalance(account.getBalance() - amountToWithdraw);
        db.update(accountId, account);
    }

    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        Account account = db.getUserAccountById(accountId);
        if(account == null) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND.label);
        }
        account.setBalance(account.getBalance() + amountToDeposit);
        db.update(accountId, account);
    }

    public Long getAccountBalance(Long accountId) {
        return db.getUserAccountById(accountId).getBalance();
    }
}
