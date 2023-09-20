package ru.clevertec.cleverbank.exeption;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
        this("Client not found");
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
