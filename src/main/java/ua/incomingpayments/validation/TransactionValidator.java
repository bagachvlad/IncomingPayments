package ua.incomingpayments.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.entity.Transaction;
import ua.incomingpayments.exceptions.FieldValidationException;
import ua.incomingpayments.exceptions.ValidationException;
import ua.incomingpayments.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionValidator implements Validator<RequestDto> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionValidator.class);

    private static final String VALIDATION_EXCEPTION_PATTERN = "Field: '%s', value: '%s' , reason: '%s'";
    private static final String DUPLICATION_REASON = "Duplicated instance in database";

    private final TransactionRepository repository;

    public TransactionValidator(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(RequestDto dto) throws ValidationException {
        List<Transaction> transactions = repository.findAll();
        try {
            transactions.forEach(transaction -> validateFields(dto, transaction));
        } catch (FieldValidationException ex) {
            String message = generateValidationMessage(ex);
            LOG.warn(message);
            throw new ValidationException(message);
        }
    }

    private String generateValidationMessage(FieldValidationException ex) {
        String invalidFieldName = ex.getInvalidFieldName();
        String invalidValue = ex.getInvalidValue();
        String reason = ex.getReason();
        return String.format(VALIDATION_EXCEPTION_PATTERN, invalidFieldName, invalidValue, reason);
    }

    public void validateFields(RequestDto dto, Transaction transaction) {
        requestIdValidator(dto, transaction);
    }

    private void requestIdValidator(RequestDto dto, Transaction transaction) {
        boolean checkId = transaction.getRequestId().equals(dto.getRequestId());
        if (checkId) {
            throw new FieldValidationException("id", dto.getRequestId().toString(), DUPLICATION_REASON);
        }
    }
}
