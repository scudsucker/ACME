package com.acme.test01.anthonybuckland.exceptions;

public class DepositAmountTooSmallException extends RuntimeException {
    public DepositAmountTooSmallException(String message) {
        super(message);
    }
}
