package pets.ui.mpa.connector;

import org.springframework.stereotype.Component;

import pets.models.model.TransactionFilters;
import pets.models.model.TransactionRequest;
import pets.models.model.TransactionResponse;
import pets.service.service.TransactionServiceSvc;

@Component
public class TransactionConnectorUi {
	
	private final TransactionServiceSvc transactionServiceSvc;

	public TransactionConnectorUi(TransactionServiceSvc transactionServiceSvc) {
		this.transactionServiceSvc = transactionServiceSvc;
	}

	public TransactionResponse getTransactionById(String username, String id) {
		return transactionServiceSvc.getTransactionById(username, id, true);
	}

	public TransactionResponse getTransactionsByUsername(String username, TransactionFilters transactionFilters) {
		return transactionServiceSvc.getTransactionsByUser(username, transactionFilters, true);
	}

	public TransactionResponse saveNewTransaction(String username, TransactionRequest transactionRequest) {
		return transactionServiceSvc.saveNewTransaction(username, transactionRequest, true);
	}

	public TransactionResponse updateTransaction(String username, String id, TransactionRequest transactionRequest) {
		return transactionServiceSvc.updateTransaction(username, id, transactionRequest, true);
	}

	public TransactionResponse deleteTransaction(String id) {
		return transactionServiceSvc.deleteTransaction(id);
	}
}
