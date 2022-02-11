package ua.incomingpayments.controller;


import org.springframework.web.bind.annotation.*;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.dto.ResponseDto;
import ua.incomingpayments.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/transferMoney")
    public void requestOnTransferMoney(@RequestBody RequestDto dto) {
        service.requestValid(dto);
    }

    @PostMapping("/moneyTransaction")
    public ResponseDto moneyTransaction(@RequestBody RequestDto dto) {
       return service.paymentTransaction(dto);
    }
}
