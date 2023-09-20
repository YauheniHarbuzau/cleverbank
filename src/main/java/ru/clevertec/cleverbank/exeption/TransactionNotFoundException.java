package ru.clevertec.cleverbank.exeption;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        this("Transaction not found");
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
