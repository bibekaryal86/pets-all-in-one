package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static pets.service.utils.CategoryTypeHelper.applyUsedInTransactionsOnlyFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import pets.models.model.RefCategoryType;
import pets.models.model.RefCategoryTypeResponse;
import pets.models.model.Status;
import pets.models.model.TransactionResponse;
import pets.service.connector.RefCategoryTypeConnectorSvc;

@Service
public class RefCategoryTypeServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefCategoryTypeServiceSvc.class);

    private final RefCategoryTypeConnectorSvc categoryTypeConnector;
    private final TransactionServiceSvc transactionService;

    public RefCategoryTypeServiceSvc(RefCategoryTypeConnectorSvc categoryTypeConnector,
                               @Lazy TransactionServiceSvc transactionService) {
        this.categoryTypeConnector = categoryTypeConnector;
        this.transactionService = transactionService;
    }

    public RefCategoryTypeResponse getAllCategoryTypes(String username, boolean usedInTxnsOnly) {
    	logger.info("Get All Category Types: {} | {}", username, usedInTxnsOnly);
        RefCategoryTypeResponse refCategoryTypeResponse;
        
        try {
            refCategoryTypeResponse = categoryTypeConnector.getAllCategoryTypes();
        } catch (Exception ex) {
            logger.error("Exception in Get All Category Types: {} | {}", username, usedInTxnsOnly, ex);
            return exception("Category Types Unavailable! Please Try Again!!!", ex.toString());
        }

        if (usedInTxnsOnly) {
            TransactionResponse transactionResponse = transactionService.getTransactionsByUser(username, null, true);
            refCategoryTypeResponse = applyUsedInTransactionsOnlyFilter(refCategoryTypeResponse.getRefCategoryTypes(), transactionResponse.getTransactions());
        }

        return refCategoryTypeResponse;
    }

    public RefCategoryTypeResponse getCategoryTypeById(String id) {
    	logger.info("Get Category Type By Id: {}", id);
        RefCategoryTypeResponse refCategoryTypesResponse = getAllCategoryTypes(null, false);
        RefCategoryType categoryType;

        if (refCategoryTypesResponse.getStatus() == null) {
            categoryType = refCategoryTypesResponse.getRefCategoryTypes().stream()
                    .filter(refCategoryType -> refCategoryType.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (categoryType == null) {
                logger.error("Category Type Not Found for Id: {}", id);
                return exception("Category Type Unavailable! Please Try Again!!!", null);
            } else {
                return RefCategoryTypeResponse.builder()
                        .refCategoryTypes(singletonList(categoryType))
                        .build();
            }
        } else {
            return refCategoryTypesResponse;
        }
    }

    private RefCategoryTypeResponse exception(String errMsg, String message) {
        return RefCategoryTypeResponse.builder()
                .refCategoryTypes(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
