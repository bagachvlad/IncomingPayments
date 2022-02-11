package ua.incomingpayments.validation;

import ua.incomingpayments.exceptions.ValidationException;

public interface Validator<V>{
    void validate(V v) throws ValidationException;
}
