package ru.clevertec.cleverbank.exeption;

public class IdNotCorrectException extends RuntimeException {

    public IdNotCorrectException() {
        this("ID is not correct");
    }

    public IdNotCorrectException(String message) {
        super(message);
    }
}
