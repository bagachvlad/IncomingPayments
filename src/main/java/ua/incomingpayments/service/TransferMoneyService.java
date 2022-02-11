package ua.incomingpayments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.entity.Account;
import ua.incomingpayments.entity.Status;
import ua.incomingpayments.entity.Transaction;
import ua.incomingpayments.exceptions.FieldNotFoundException;
import ua.incomingpayments.mapper.TransactionMapper;
import ua.incomingpayments.repository.AccountRepository;
import ua.incomingpayments.repository.StatusRepository;
import ua.incomingpayments.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransferMoneyService {

    private static final String VALIDATION_FAILED_PATTERN = "Field not found";

    private final TransactionRepository transaction;
    private final AccountRepository account;
    private final StatusRepository status;
    private final TransactionMapper mapper;

    public TransferMoneyService(TransactionRepository transaction, AccountRepository account, StatusRepository status, TransactionMapper mapper) {
        this.transaction = transaction;
        this.account = account;
        this.status = status;
        this.mapper = mapper;
    }

    @Transactional
    public Transaction saveTransaction(RequestDto dto) {

        Transaction transaction1 = mapper.mapDtoToTransaction(dto);
        Status error = status.findStatusByValue("ERROR");
        Status success = status.findStatusByValue("SUCCESS");

        Account from = account.findById(dto.getAccountFrom()).orElseThrow(() -> new FieldNotFoundException(VALIDATION_FAILED_PATTERN));
        Account to = account.findById(dto.getAccountTo()).orElseThrow(() -> new FieldNotFoundException(VALIDATION_FAILED_PATTERN));
        BigDecimal fromBalance = from.getAmount();
        BigDecimal toBalance = to.getAmount();

        if (!fromBalance.equals(dto.getAmount())) {
            transaction1.setStatus(error);
        }

        transaction1.setStatus(success);

        BigDecimal newFrom = fromBalance.subtract(dto.getAmount());
        BigDecimal newTo = toBalance.add(dto.getAmount());

        from.setAmount(newFrom);
        account.save(from);

        to.setAmount(newTo);
        account.save(to);

        return transaction.save(transaction1);
    }
}
