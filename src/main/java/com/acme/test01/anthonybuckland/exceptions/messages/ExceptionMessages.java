package com.acme.test01.anthonybuckland.exceptions.messages;

public enum ExceptionMessages {
    CANNOT_OPEN_SAVINGS_UNDER_2000("Error: You cannot open a savings account with an amount less than R2000"),
    CANNOT_OPEN_CURRENT_UNDER_ZERO("Error: You cannot open a current account with an amount less than R0"),
    CANNOT_OPEN_ACCOUNT_WHEN_ACCOUNT_ID_EXISTS("Error: The accountId you are using already exists"),
    CANNOT_WITHDRAW_MORE_THAN_THE_LIMIT("Error: You are trying to withdraw more than your account limit allows"),
    ACCOUNT_NOT_FOUND("Error: the account you are trying to access does not exist");

    public final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }
}
