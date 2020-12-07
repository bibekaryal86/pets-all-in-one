package pets.service.service;

import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;
import static pets.service.utils.TransactionHelper.applyAllDetailsStatic;
import static pets.service.utils.TransactionHelper.applyFilters;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pets.models.model.AccountResponse;
import pets.models.model.RefCategoryResponse;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;
import pets.models.model.RefTransactionTypeResponse;
import pets.models.model.Status;
import pets.models.model.TransactionFilters;
import pets.models.model.TransactionRequest;
import pets.models.model.TransactionResponse;
import pets.service.connector.TransactionConnectorSvc;

@Service
public class TransactionServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceSvc.class);
    private final TransactionConnectorSvc transactionConnector;
    private AccountServiceSvc accountService;
    private final RefCategoryServiceSvc categoryService;
    private final RefMerchantServiceSvc merchantService;
    private final RefTransactionTypeServiceSvc transactionTypeService;

    public TransactionServiceSvc(TransactionConnectorSvc transactionConnector,
                              RefCategoryServiceSvc categoryService,
                              RefMerchantServiceSvc merchantService,
                              RefTransactionTypeServiceSvc transactionTypeService) {
        this.transactionConnector = transactionConnector;
        this.categoryService = categoryService;
        this.merchantService = merchantService;
        this.transactionTypeService = transactionTypeService;
    }

    // to avoid circular dependency
    // maybe add a new service like TransactionHelperService to avoid it
    @Autowired
    public void setAccountService(AccountServiceSvc accountService) {
        this.accountService = accountService;
    }

    public TransactionResponse getTransactionById(String username, String id, boolean applyAllDetails) {
    	logger.info("Get Transaction By Id: {} | {} | {}", username, id, applyAllDetails);
        TransactionResponse transactionResponse;

        try {
            transactionResponse = transactionConnector.getTransactionById(id);
        } catch (Exception ex) {
            logger.error("Exception in Get Transaction By Id: {} | {} | {}", username, id, applyAllDetails, ex);
            transactionResponse = exception("Transaction Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(transactionResponse.getTransactions()) &&
                applyAllDetails) {
            applyAllDetails(username, transactionResponse);
        }

        return transactionResponse;
    }

    public TransactionResponse getTransactionsByUser(String username,
                                                     TransactionFilters transactionFilters,
                                                     boolean applyAllDetails) {
    	logger.info("Get Transactions By User: {} | {} | {}", username, transactionFilters, applyAllDetails);
        TransactionResponse transactionResponse;

        try {
            transactionResponse = transactionConnector.getTransactionsByUser(username);
        } catch (Exception ex) {
            logger.error("Exception in Get Transactions By User: {} | {} | {}", username, transactionFilters, applyAllDetails, ex);
            transactionResponse = exception("Transactions Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(transactionResponse.getTransactions())) {
            if (applyAllDetails) {
                applyAllDetails(username, transactionResponse);
            }

            if (transactionFilters != null) {
                transactionResponse = applyFilters(transactionResponse, transactionFilters);
            }
        }

        return transactionResponse;
    }

    public TransactionResponse saveNewTransaction(String username,
                                                  TransactionRequest transactionRequest,
                                                  boolean applyAllDetails) {
    	logger.info("Save New Transaction: {} | {} | {}", username, transactionRequest, applyAllDetails);
        TransactionResponse transactionResponse;

        // check if user selected to enter a new merchant
        if (hasText(transactionRequest.getNewMerchant())) {
            // save merchant first and set it as merchant id in transaction request
            RefMerchantResponse refMerchantResponse = merchantService.saveNewMerchant(RefMerchantRequest.builder()
                    .username(username)
                    .description(transactionRequest.getNewMerchant())
                    .build());

            if (refMerchantResponse.getStatus() == null &&
                    !isEmpty(refMerchantResponse.getRefMerchants())) {
                String newMerchantId = refMerchantResponse.getRefMerchants().get(0).getId();
                logger.info("Save New Transaction, New Merchant Id: {} | {}", username, newMerchantId);
                transactionRequest.setMerchantId(newMerchantId);
            } else {
                logger.error("Error Saving New Merchant for Transaction: {} | {}", username, transactionRequest.getNewMerchant());
                return exception("Save Transaction Unavailable! New Merchant Not Saved!!!", null);
            }
        }

        try {
            transactionResponse = transactionConnector.saveNewTransaction(transactionRequest);
        } catch (Exception ex) {
            logger.error("Exception in Save New Transaction: {} | {} | {}", username, transactionRequest, applyAllDetails, ex);
            transactionResponse = exception("Save Transaction Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(transactionResponse.getTransactions()) &&
                applyAllDetails) {
            applyAllDetails(username, transactionResponse);
        }

        return transactionResponse;
    }

    public TransactionResponse updateTransaction(String username, String id,
                                                 TransactionRequest transactionRequest,
                                                 boolean applyAllDetails) {
    	logger.info("Update Transaction: {} | {} | {} | {}", username, id, transactionRequest, applyAllDetails);
        TransactionResponse transactionResponse;

        // check if user selected to enter a new merchant
        if (hasText(transactionRequest.getNewMerchant())) {
            // save merchant first and set it as merchant id in transaction request
            RefMerchantResponse merchantResponse = merchantService.saveNewMerchant(RefMerchantRequest.builder()
                    .username(username)
                    .description(transactionRequest.getNewMerchant())
                    .build());

            if (merchantResponse.getStatus() == null &&
                    !isEmpty(merchantResponse.getRefMerchants())) {
                String newMerchantId = merchantResponse.getRefMerchants().get(0).getId();
                logger.info("Update Transaction, New Merchant Id: {} | {}", username, newMerchantId);
                transactionRequest.setMerchantId(newMerchantId);
            } else {
                logger.error("Error Saving New Merchant for Transaction: {} | {}", username, transactionRequest.getNewMerchant());
                return exception("Update Transaction Unavailable! New Merchant Not Saved!!!", null);
            }
        }

        try {
            transactionResponse = transactionConnector.updateTransaction(id, transactionRequest);
        } catch (Exception ex) {
            logger.error("Exception in Update Transaction: {} | {} | {} | {}", username, id, transactionRequest, applyAllDetails, ex);
            transactionResponse = exception("Update Transaction Unavailable! Please Try Again!!!", null);
        }

        if (!isEmpty(transactionResponse.getTransactions()) &&
                applyAllDetails) {
            applyAllDetails(username, transactionResponse);
        }

        return transactionResponse;
    }

    public TransactionResponse deleteTransaction(String id) {
    	logger.info("Delete Transaction: {}", id);
    	
        try {
            return transactionConnector.deleteTransaction(id);
        } catch (Exception ex) {
            logger.error("Exception in Delete Transaction: {}", id, ex);
            return exception("Delete Transaction Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public TransactionResponse deleteTransactionsByAccount(String accountId) {
    	logger.info("Delete Transaction By Account: {}", accountId);
    	
        try {
            return transactionConnector.deleteTransactionsByAccount(accountId);
        } catch (Exception ex) {
            logger.error("Exception in Delete Transactions by Account: {}", accountId, ex);
            return exception("Delete Transaction by Account Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    private void applyAllDetails(String username, TransactionResponse transactionResponse) {
        CompletableFuture<AccountResponse> accountResponseCompletableFuture = accountService.getAccountsByUserFuture(username);
        CompletableFuture<RefCategoryResponse> refCategoryResponseCompletableFuture = categoryService.getAllCategoriesFuture();
        CompletableFuture<RefMerchantResponse> refMerchantResponseCompletableFuture = merchantService.getMerchantsByUserFuture(username);
        CompletableFuture<RefTransactionTypeResponse> refTransactionTypeResponseCompletableFuture = transactionTypeService.getAllTransactionTypesFuture();

        applyAllDetailsStatic(transactionResponse, accountResponseCompletableFuture, refCategoryResponseCompletableFuture,
                refMerchantResponseCompletableFuture, refTransactionTypeResponseCompletableFuture);
    }

    private TransactionResponse exception(String errMsg, String message) {
    	return TransactionResponse.builder()
                .transactions(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
