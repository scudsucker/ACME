package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.accounts.utility.CommonAccountUtility;
import com.acme.test01.anthonybuckland.database.Database;
import com.acme.test01.anthonybuckland.database.SystemDB;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.AccountNotFoundException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.anthonybuckland.models.Account;
import com.acme.test01.anthonybuckland.models.AccountType;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;

public class CurrentAccount extends AbstractCurrentAccount {

    private static final long MINIMUM_BALANCE = -10000000L;
    private final Database db = SystemDB.getInstance();
    private final CommonAccountUtility utility = new CommonAccountUtility();

    public void openCurrentAccount(Long accountId, Long amountToDeposit) {
        if (db.getUserAccountById(accountId) != null) {
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
        utility.withdraw(accountId, amountToWithdraw, MINIMUM_BALANCE);
    }

    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        utility.deposit(accountId, amountToDeposit);
    }

    public Long getAccountBalance(Long accountId) {
        return utility.getAccountBalance(accountId);
    }
}

