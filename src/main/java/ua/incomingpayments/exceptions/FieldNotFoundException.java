package ua.incomingpayments.exceptions;

public class FieldNotFoundException extends RuntimeException{

    public FieldNotFoundException() {
    }

    public FieldNotFoundException(String message) {
        super(message);
    }
}
