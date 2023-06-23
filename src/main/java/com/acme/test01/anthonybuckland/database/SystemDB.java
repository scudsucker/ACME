package com.acme.test01.anthonybuckland.database;

import com.acme.test01.anthonybuckland.models.Account;
import com.acme.test01.anthonybuckland.models.AccountType;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// NOTE: this class inherits from an interface, to allow multiple possible DB implementations
// For the purposes of this test, there is only one implementation which is an anti-pattern.
public final class SystemDB implements Database {

    private static SystemDB INSTANCE;

    private final Map<Long, Account> userAccounts = new HashMap<>();

    public SystemDB() {
        this.userAccounts.put(1L, new Account(AccountType.SAVINGS, 200000L));
        this.userAccounts.put(2L, new Account(AccountType.SAVINGS, 500000L));
        this.userAccounts.put(3L, new Account(AccountType.CURRENT, 100000L, 1000000L));
        this.userAccounts.put(4L, new Account(AccountType.CURRENT, 200000L, 2000000L));
    }


    // NOTE - in a multithreaded environment this should also be synchronized,
    // For the purposes of this test it is not.
    public static SystemDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SystemDB();
        }
        return INSTANCE;
    }

    public Account getUserAccountById(Long accountId) {
        return userAccounts.get(accountId);
    }

    public Account update(Long accountId, Account userAccount) {
        this.userAccounts.put(accountId, userAccount);
        return userAccounts.get(accountId);
    }

    public Account create(Long accountId, Account userAccount) {
        this.userAccounts.put(accountId, userAccount);
        return userAccounts.get(accountId);
    }
}
