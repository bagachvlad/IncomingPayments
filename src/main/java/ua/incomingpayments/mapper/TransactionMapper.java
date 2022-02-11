package ua.incomingpayments.mapper;

import org.springframework.stereotype.Service;
import ua.incomingpayments.dto.RequestDto;
import ua.incomingpayments.dto.ResponseDto;
import ua.incomingpayments.entity.Account;
import ua.incomingpayments.entity.Status;
import ua.incomingpayments.entity.Transaction;

import java.util.UUID;

@Service
public class TransactionMapper {

    public Transaction mapDtoToTransaction(RequestDto dto) {
        Transaction transaction = new Transaction();
        transaction.setRequestId(dto.getRequestId());

        Account fromAccount = new Account();
        fromAccount.setId(dto.getAccountFrom());

        transaction.setAccountFrom(fromAccount);

        Account toAccount = new Account();
        toAccount.setId(dto.getAccountTo());

        transaction.setAccountTo(toAccount);
        transaction.setAmount(dto.getAmount());
        transaction.setInitiator("");
        transaction.setStatus(new Status());
        transaction.setDescription("");
        return transaction;
    }

    public ResponseDto mapTransactionToDto(Transaction transaction) {
        ResponseDto dto = new ResponseDto();

        Transaction status = new Transaction();
        String result = status.getStatus().getValue();

        dto.setResult(result);
        dto.setDescription(transaction.getDescription());

        return dto;
    }

    private void populateId(RequestDto dto) {
        UUID id = UUID.randomUUID();
        dto.setRequestId(id);
    }
}
