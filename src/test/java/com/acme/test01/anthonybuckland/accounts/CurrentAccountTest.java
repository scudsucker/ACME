package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.database.SystemDB;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;

public class CurrentAccountTest {

    /*
        For a Current Account, withdraw works as follows:
            a. Current accounts can have an overdraft limit (the maximum overdraft limit
            allowed on a Current Account by Acme Bank is R100 000.00).
            b. This means that a current account can have both a positive or negative
            balance.
            c. One cannot withdraw more than the (balance + overdraft limit) on a current
            account.

        Thus, as an example if one has a current account with a positive balance of R5000.00, and an overdraft limit of R25 000.00, then the maximum one can
        withdraw is R30 000.00.
        As another example, if one has a current account with a negative balance of
        R3000.00, and an overdraft limit of R20 000.00, then the maximum one can
        withdraw is R17 000.00.
        For a Current Account, deposit works as follows:
            a. A Current account has no minimum deposit requirement
            b. A Current account’s balance is increased by the amount deposited
     */
    private static SystemDB systemDB;

    @BeforeEach
    public void init() {
        systemDB = new SystemDB();
    }

    @Test
    public void canOpenAccount_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 5L;
        long amountToDeposit = 200000L;

        sut.openCurrentAccount(accountId, amountToDeposit);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(accountBalance, amountToDeposit);
    }

    @Test
    public void canOpenEmptyAccount_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 5L;

        sut.openCurrentAccount(accountId, 0L);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(accountBalance, 0L);
    }

    @Test
    public void canOpenEmptyAccountWithoutBalance_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 5L;

        sut.openCurrentAccount(accountId);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(accountBalance, 0L);
    }

    @Test
    public void cannotOpenAccountWithNegativeFunds_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 5L;
        long amountToDeposit = -100000L; // less than the minimum overdraft

        DepositAmountTooSmallException thrown = Assertions.assertThrows(DepositAmountTooSmallException.class, () -> {
            sut.openCurrentAccount(accountId, amountToDeposit);
        });

        Assertions.assertEquals(CANNOT_OPEN_CURRENT_UNDER_ZERO.label, thrown.getMessage());
    }

    @Test
    public void cannotOpenAccountWithExistingId_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 3L; // existing ID
        long amountToDeposit = 200000L;

        AccountAlreadyExistsException thrown = Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {
            sut.openCurrentAccount(accountId, amountToDeposit);
        });

        Assertions.assertEquals(CANNOT_OPEN_ACCOUNT_WHEN_ACCOUNT_ID_EXISTS.label, thrown.getMessage());
    }

    @Test
    public void canDeposit_Test() {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 3L; // has 100000L balance
        long amountToDeposit = 200000L;
        long currentBalance = 100000L;
        long expected = amountToDeposit + currentBalance;

        // Warning: Can throw an exception if the Long currentBalance is greater than int.MAX
        sut.deposit(accountId, Math.toIntExact(amountToDeposit));

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(expected, accountBalance);
    }

    @Test
    public void canWithdraw_Test() throws WithdrawalAmountTooLargeException {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 4L; // has 200000L balance
        long amountToWithdraw = 100000L;
        long expected = 100000L;

        sut.withdraw(accountId, (int) amountToWithdraw);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(expected, accountBalance);
    }

    @Test
    public void canGoIntoOverdraft_Test() throws WithdrawalAmountTooLargeException {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 4L; // has 200000L balance
        long amountToWithdraw = 300000L;
        long expected = -100000;

        sut.withdraw(accountId, (int) amountToWithdraw);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(expected, accountBalance);
    }

    @Test
    public void cannotGoBeyondOverdraftLimit_Test() throws WithdrawalAmountTooLargeException {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 4L; // has 200000L balance
        long amountToWithdraw = 50000000L; // very large

        WithdrawalAmountTooLargeException thrown = Assertions.assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            sut.withdraw(accountId, (int) amountToWithdraw);
        });

        Assertions.assertEquals(thrown.getMessage(), CANNOT_WITHDRAW_MORE_THAN_THE_LIMIT.label);

    }


    @Test
    public void cannotWithdrawWithInsufficientFunds_Test() throws WithdrawalAmountTooLargeException  {
        CurrentAccount sut = new CurrentAccount(systemDB);

        long accountId = 3L; // has 500000L balance
        long amountToWithdraw = 100000L;

        sut.withdraw(accountId, (int) amountToWithdraw);
    }



}
