package pets.service.connector;

import org.springframework.stereotype.Component;

import lombok.NonNull;
import pets.database.service.TransactionServiceDb;
import pets.models.model.TransactionRequest;
import pets.models.model.TransactionResponse;

@Component
public class TransactionConnectorSvc {
	
	private final TransactionServiceDb transactionServiceDb;

    public TransactionConnectorSvc(TransactionServiceDb transactionServiceDb) {
        this.transactionServiceDb = transactionServiceDb;
    }

    public TransactionResponse getTransactionById(@NonNull final String id) {
        return transactionServiceDb.getTransactionById(id);
    }

    public TransactionResponse getTransactionsByUser(@NonNull final String username) {
        return transactionServiceDb.getTransactionsByUser(username);
    }

    public TransactionResponse saveNewTransaction(@NonNull final TransactionRequest transactionRequest) {
        return transactionServiceDb.saveNewTransaction(transactionRequest);
    }

    public TransactionResponse updateTransaction(@NonNull final String id,
                                                 @NonNull final TransactionRequest transactionRequest) {
        return transactionServiceDb.updateTransactionById(id, transactionRequest);
    }

    public TransactionResponse deleteTransaction(@NonNull final String id) {
        return transactionServiceDb.deleteTransactionById(id);
    }

    public TransactionResponse deleteTransactionsByAccount(@NonNull final String accountId) {
        return transactionServiceDb.deleteTransactionsByAccountId(accountId);
    }
}
