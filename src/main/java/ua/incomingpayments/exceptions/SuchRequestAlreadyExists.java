package ua.incomingpayments.exceptions;

public class SuchRequestAlreadyExists extends RuntimeException {

    public SuchRequestAlreadyExists() {
    }

    public SuchRequestAlreadyExists(String message) {
        super(message);
    }
}
