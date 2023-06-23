package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.database.SystemDB;
import com.acme.test01.anthonybuckland.exceptions.AccountAlreadyExistsException;
import com.acme.test01.anthonybuckland.exceptions.DepositAmountTooSmallException;
import com.acme.test01.anthonybuckland.exceptions.WithdrawalAmountTooLargeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.acme.test01.anthonybuckland.exceptions.messages.ExceptionMessages.*;

public class SavingsAccountTest {

    /*
    For a Savings Account, withdraw works as follows:
        a. A Savings account must have a minimum balance of R2000.00 at all times
        b. A Savings account’s balance is decreased by the amount withdrawn
    For a Savings Account, deposit works as follows:
        a. A Savings account can only be opened if you deposit at least R2000.00 in it
        b. A Savings account’s balance is increased by the amount deposited
     */

    private static SystemDB systemDB;

    @BeforeEach
    public void init() {
        systemDB = SystemDB.getInstance();
        systemDB.resetDbForTests();
    }

    @Test
    public void canOpenAccount_Test() {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 5L;
        long amountToDeposit = 200000L;

        sut.openSavingsAccount(accountId, amountToDeposit);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(accountBalance, amountToDeposit);
    }

    @Test
    public void cannotOpenAccountWithInsufficientFunds_Test() {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 5L;
        long amountToDeposit = 100000L; // less than the minimum

        DepositAmountTooSmallException thrown = Assertions.assertThrows(DepositAmountTooSmallException.class, () -> {
            sut.openSavingsAccount(accountId, amountToDeposit);
        });

        Assertions.assertEquals(CANNOT_OPEN_SAVINGS_UNDER_2000.label, thrown.getMessage());
    }

    @Test
    public void cannotOpenAccountWithExistingId_Test() {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 2L; // existing ID
        long amountToDeposit = 200000L;

        AccountAlreadyExistsException thrown = Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {
            sut.openSavingsAccount(accountId, amountToDeposit);
        });

        Assertions.assertEquals(CANNOT_OPEN_ACCOUNT_WHEN_ACCOUNT_ID_EXISTS.label, thrown.getMessage());
    }

    @Test
    public void canDeposit_Test() {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 2L;
        long amountToDeposit = 200000L;
        long currentBalance = 500000L;
        long expected = amountToDeposit + currentBalance;

        // Warning: Can throw an exception if the Long currentBalance is greater than int.MAX
        sut.deposit(accountId, Math.toIntExact(amountToDeposit));

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(expected, accountBalance);
    }

    @Test
    public void canWithdraw_Test() throws WithdrawalAmountTooLargeException {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 2L; // has 500000L balance
        long amountToWithdraw = 100000L;
        long expected = 400000L;

        sut.withdraw(accountId, (int) amountToWithdraw);

        Long accountBalance = sut.getAccountBalance(accountId);

        Assertions.assertEquals(expected, accountBalance);
    }

    @Test
    public void cannotWithdrawWithInsufficientFunds_Test() throws WithdrawalAmountTooLargeException  {
        SavingsAccount sut = new SavingsAccount();

        long accountId = 2L; // has 500000L balance
        long amountToWithdraw = 100000L;

        sut.withdraw(accountId, (int) amountToWithdraw);
    }



}
