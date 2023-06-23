package com.acme.test01.anthonybuckland.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
