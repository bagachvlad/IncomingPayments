package ua.incomingpayments.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.entity.Account;
import ua.incomingpayments.exceptions.FieldValidationException;
import ua.incomingpayments.exceptions.ValidationException;
import ua.incomingpayments.repository.AccountRepository;

import java.util.List;

@Service
public class AccountValidator implements Validator<RequestDto> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountValidator.class);

    private static final String VALIDATION_EXCEPTION_PATTERN = "Field: '%s', value: '%s' , reason: '%s'";
    private static final String ABSENCE_REASON = "Absence instance in database";

    private AccountRepository repository;

    public AccountValidator(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(RequestDto requestDto) throws ValidationException {
        List<Account> accounts = repository.findAll();
        try {
            accounts.forEach(account -> validateFields(requestDto, account));
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

    private void validateFields(RequestDto dto, Account account) {
        accountFromValidation(dto, account);
        accountToValidation(dto, account);
    }

    private void accountFromValidation(RequestDto dto, Account account) {
        boolean checkId = account.getId().equals(dto.getAccountFrom());
        if (checkId) {
            throw new FieldValidationException("id", dto.getAccountFrom().toString(), ABSENCE_REASON);
        }
    }

    private void accountToValidation(RequestDto dto, Account account) {
        boolean checkId = account.getId().equals(dto.getAccountTo());
        if (checkId) {
            throw new FieldValidationException("id", dto.getAccountTo().toString(), ABSENCE_REASON);
        }
    }
}
