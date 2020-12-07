package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;
import static pets.service.utils.CategoryHelper.applyAllDetailsStatic;
import static pets.service.utils.CategoryHelper.applyFilters;
import static pets.service.utils.CategoryHelper.sortWithinRefCategoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import pets.models.model.RefCategory;
import pets.models.model.RefCategoryFilters;
import pets.models.model.RefCategoryResponse;
import pets.models.model.RefCategoryTypeResponse;
import pets.models.model.Status;
import pets.models.model.Transaction;
import pets.service.connector.RefCategoryConnectorSvc;

@Service
public class RefCategoryServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefCategoryServiceSvc.class);

    private final RefCategoryConnectorSvc categoryConnector;
    private final RefCategoryTypeServiceSvc categoryTypeService;
    private final TransactionServiceSvc transactionService;

    public RefCategoryServiceSvc(RefCategoryConnectorSvc categoryConnector,
                           RefCategoryTypeServiceSvc categoryTypeService,
                           @Lazy TransactionServiceSvc transactionService) {
        this.categoryConnector = categoryConnector;
        this.categoryTypeService = categoryTypeService;
        this.transactionService = transactionService;
    }

    public RefCategoryResponse getAllCategories(String username, RefCategoryFilters refCategoryFilters) {
    	logger.info("Get All Categories: {} | {}", username, refCategoryFilters);
        RefCategoryResponse categoryResponse;
        
        try {
            categoryResponse = categoryConnector.getAllCategories();
        } catch (Exception ex) {
            logger.error("Exception in Get All Categories: {} | {}", username, refCategoryFilters, ex);
            categoryResponse = exception("Categories Unavailable! Please Try Again!!!", ex.toString());
        }

        if (refCategoryFilters != null) {
            List<Transaction> transactions = new ArrayList<>();
            if (refCategoryFilters.isUsedInTxnsOnly()) {
                transactions = transactionService.getTransactionsByUser(username, null, false)
                        .getTransactions();
            }

            categoryResponse = applyFilters(categoryResponse, refCategoryFilters, transactions);
        }

        if (!isEmpty(categoryResponse.getRefCategories())) {
            applyAllDetails(categoryResponse);
            categoryResponse = sortWithinRefCategoryType(categoryResponse);
        }

        return categoryResponse;
    }

    public CompletableFuture<RefCategoryResponse> getAllCategoriesFuture() {
        return CompletableFuture.supplyAsync(() -> getAllCategories(null, null));
    }

    public RefCategoryResponse getCategoryById(String id) {
    	logger.info("Get Category By Id: {}", id);
        RefCategoryResponse categoryResponse = getAllCategories(null, null);
        RefCategory category;

        if (categoryResponse.getStatus() == null) {
            category = categoryResponse.getRefCategories().stream()
                    .filter(refCategory -> refCategory.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (category == null) {
                logger.error("Category Not Found for Id: {}", id);
                return exception("Category Unavailable! Please Try Again!!!", null);
            } else {
                return RefCategoryResponse.builder()
                        .refCategories(singletonList(category))
                        .build();
            }
        } else {
            return categoryResponse;
        }
    }

    private void applyAllDetails(RefCategoryResponse categoryResponse) {
        RefCategoryTypeResponse categoryTypeResponse = categoryTypeService.getAllCategoryTypes(null, false);
        applyAllDetailsStatic(categoryResponse, categoryTypeResponse.getRefCategoryTypes());
    }

    private RefCategoryResponse exception(String errMsg, String message) {
        return RefCategoryResponse.builder()
                .refCategories(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
