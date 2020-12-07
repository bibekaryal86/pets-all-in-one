package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.RefTransactionType;
import pets.models.model.RefTransactionTypeResponse;
import pets.models.model.Status;
import pets.service.connector.TransactionTypeConnectorSvc;

@Service
public class RefTransactionTypeServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefTransactionTypeServiceSvc.class);

    private final TransactionTypeConnectorSvc transactionTypeConnector;

    public RefTransactionTypeServiceSvc(TransactionTypeConnectorSvc transactionTypeConnector) {
        this.transactionTypeConnector = transactionTypeConnector;
    }

    public RefTransactionTypeResponse getAllTransactionTypes() {
    	logger.info("Get All Transaction Types");
    	
        try {
            return transactionTypeConnector.getAllTransactionTypes();
        } catch (Exception ex) {
            logger.error("Exception in Get All Transaction Types", ex);
            return exception("Transaction Types Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public CompletableFuture<RefTransactionTypeResponse> getAllTransactionTypesFuture() {
        return CompletableFuture.supplyAsync(this::getAllTransactionTypes);
    }

    public RefTransactionTypeResponse getTransactionTypeById(String id) {
    	logger.info("Get Transaction Type By Id: {}", id);
        RefTransactionTypeResponse refTransactionTypesResponse = getAllTransactionTypes();
        RefTransactionType transactionType;

        if (refTransactionTypesResponse.getStatus() == null) {
            transactionType = refTransactionTypesResponse.getRefTransactionTypes().stream()
                    .filter(refTransactionType -> refTransactionType.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (transactionType == null) {
                logger.error("Transaction Type Not Found for Id: {}", id);
                return exception("Transaction Type Unavailable! Please Try Again!!!", null);
            } else {
                return RefTransactionTypeResponse.builder()
                        .refTransactionTypes(singletonList(transactionType))
                        .build();
            }
        } else {
            return refTransactionTypesResponse;
        }
    }

    private RefTransactionTypeResponse exception(String errMsg, String message) {
        return RefTransactionTypeResponse.builder()
                .refTransactionTypes(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
