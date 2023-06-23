package com.acme.test01.anthonybuckland.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
