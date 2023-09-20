package ru.clevertec.cleverbank.exeption;

public class EntityNotCorrectException extends RuntimeException {

    public EntityNotCorrectException() {
        this("Entity is not correct");
    }

    public EntityNotCorrectException(String message) {
        super(message);
    }
}
