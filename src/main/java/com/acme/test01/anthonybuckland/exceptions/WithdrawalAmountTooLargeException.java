package com.acme.test01.anthonybuckland.exceptions;

public class WithdrawalAmountTooLargeException extends Exception {
    public WithdrawalAmountTooLargeException(String message) {
        super(message);
    }
}
