package ru.clevertec.cleverbank.exeption;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        this("Account not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
