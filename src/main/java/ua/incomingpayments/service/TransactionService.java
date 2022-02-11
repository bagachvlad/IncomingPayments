package ua.incomingpayments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.dto.ResponseDto;
import ua.incomingpayments.entity.Transaction;
import ua.incomingpayments.exceptions.FieldNotFoundException;
import ua.incomingpayments.exceptions.SuchRequestAlreadyExists;
import ua.incomingpayments.exceptions.ValidationException;
import ua.incomingpayments.mapper.TransactionMapper;
import ua.incomingpayments.validation.AccountValidator;
import ua.incomingpayments.validation.TransactionValidator;

@Service
public class TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    private static final String VALIDATION_FAILED_PATTERN = "Validation stage is failed. Message: '%s'";
    private static final String TRANSACTION_FAILED_PATTERN = "Your transfer has been cancelled.";
    private static final String TRANSACTION_SUCCESS_PATTERN = "Your transfer was successful.";

    private final AccountValidator accountValidator;
    private final TransactionMapper mapper;
    private final TransactionValidator transactionValidator;
    private final TransferMoneyService service;

    public TransactionService(AccountValidator accountValidator, TransactionMapper mapper, TransactionValidator transactionValidator, TransferMoneyService service) {
        this.accountValidator = accountValidator;
        this.mapper = mapper;
        this.transactionValidator = transactionValidator;
        this.service = service;
    }

    public void requestValid(RequestDto dto) {
        if (!isRequestDtoAccountValid(dto)) {
            throw new FieldNotFoundException();
        }
        if (isRequestDtoIdValid(dto)) {
            throw new SuchRequestAlreadyExists();
        }
    }

    public ResponseDto paymentTransaction(RequestDto dto) {

        Transaction transaction = service.saveTransaction(dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResult(transaction.getStatus().getValue());
        if (!responseDto.getResult().equals("ERROR")) {
            responseDto.setDescription(TRANSACTION_FAILED_PATTERN);
        }
        responseDto.setDescription(TRANSACTION_SUCCESS_PATTERN);
        return mapper.mapTransactionToDto(transaction);
    }

    private boolean isRequestDtoAccountValid(RequestDto dto) {
        try {
            accountValidator.validate(dto);
        } catch (ValidationException ex) {
            String message = String.format(VALIDATION_FAILED_PATTERN, ex.getMessage());
            LOG.warn(message);
            return false;
        }
        return true;
    }

    private boolean isRequestDtoIdValid(RequestDto dto) {
        try {
            transactionValidator.validate(dto);
        } catch (ValidationException ex) {
            String message = String.format(VALIDATION_FAILED_PATTERN, ex.getMessage());
            LOG.warn(message);
            return false;
        }
        return true;
    }

}
