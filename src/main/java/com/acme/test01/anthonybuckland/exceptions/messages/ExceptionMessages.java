package com.acme.test01.anthonybuckland.exceptions.messages;

public enum ExceptionMessages {
    CANNOT_OPEN_SAVINGS_UNDER_2000("Error: You cannot open a savings account with an amount less than R2000"),
    CANNOT_OPEN_SAVINGS_ACCOUNT_WHEN_CUSTOMER_ID_EXISTS("Error: The accountId you are using already exists"),
    CANNOT_WITHDRAW_MORE_THAN_THE_LIMIT ("Error: You are trying to withdraw more than your account limit allows"),

    ACCOUNT_NOT_FOUND("Error: the account you are trying to access does not exist");

    public final String label;

    private ExceptionMessages(String label) {
        this.label = label;
    }
}
