package pets.service.connector;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import pets.database.service.RefTransactionTypeServiceDb;
import pets.models.model.RefTransactionTypeResponse;

@Component
public class TransactionTypeConnectorSvc {
	
	private final RefTransactionTypeServiceDb refTransactionTypeServiceDb;

    public TransactionTypeConnectorSvc(RefTransactionTypeServiceDb refTransactionTypeServiceDb) {
        this.refTransactionTypeServiceDb = refTransactionTypeServiceDb;
    }

    @Cacheable(value = "transactionTypes", unless = "#result==null")
    public RefTransactionTypeResponse getAllTransactionTypes() {
        return refTransactionTypeServiceDb.getAllRefTransactionTypes();
    }
}
