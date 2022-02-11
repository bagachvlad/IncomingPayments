package ua.incomingpayments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.exceptions.FieldNotFoundException;
import ua.incomingpayments.exceptions.SuchRequestAlreadyExists;

@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(value = {FieldNotFoundException.class, SuchRequestAlreadyExists.class})
    public ResponseEntity<RequestDto> requestException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
