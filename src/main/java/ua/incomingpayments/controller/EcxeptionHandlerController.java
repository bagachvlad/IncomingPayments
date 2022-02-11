package ua.incomingpayments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ua.incomingpayments.entity.Account;
import ua.incomingpayments.exceptions.FieldNotFoundException;

@RestController
public class EcxeptionHandlerController {

    @ExceptionHandler(value = FieldNotFoundException.class)
    public ResponseEntity<Account> fieldNotFoundException(FieldNotFoundException fieldNotFoundException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
