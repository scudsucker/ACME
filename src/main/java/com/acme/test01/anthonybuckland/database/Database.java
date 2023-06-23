package com.acme.test01.anthonybuckland.database;

import com.acme.test01.anthonybuckland.models.Account;

public interface Database {

    Account update(Long accountId, Account userAccount);

    Account create(Long accountId, Account userAccount);

    Account getUserAccountById(Long accountId);
}
