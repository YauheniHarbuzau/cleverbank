package ru.clevertec.cleverbank.exeption;

public class BankNotFoundException extends RuntimeException {

    public BankNotFoundException() {
        this("Bank not found");
    }

    public BankNotFoundException(String message) {
        super(message);
    }
}
