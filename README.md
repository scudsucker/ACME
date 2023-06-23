### ACME banking App ###

The ACME banking app is a simplified bank with two types of accounts, with various rules which apply 
to both, or only one type.

## Source ##
https://github.com/scudsucker/ACME

## Run tests ##
`./gradlew clean test`

## Notes ##
The current account service and the savings account service both implement `AccountService` but account service has 
both `openSavingsAccount` and `openCurrentAccount` requirements. The abstract classes hide that from each concrete 
implementation.

The methods `withdraw`, `deposit` and `getAccountBalance` are duplicated, which is a code smell.