package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;
import static pets.service.utils.MerchantHelper.applyFilters;
import static pets.service.utils.MerchantHelper.getRefMerchantsFilterListByName;
import static pets.service.utils.MerchantHelper.setSystemDependentMerchants;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import pets.models.model.RefMerchantFilters;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;
import pets.models.model.Status;
import pets.models.model.Transaction;
import pets.models.model.TransactionResponse;
import pets.service.connector.RefMerchantConnectorSvc;

@Service
public class RefMerchantServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefMerchantServiceSvc.class);
    private final RefMerchantConnectorSvc merchantConnector;
    private final TransactionServiceSvc transactionService;
    // instead of Lazy implementation, maybe add a new MerchantHelperService class
    // in order to avoid cyclical dependency

    public RefMerchantServiceSvc(RefMerchantConnectorSvc merchantConnector,
                           @Lazy TransactionServiceSvc transactionService) {
        this.merchantConnector = merchantConnector;
        this.transactionService = transactionService;
    }

    public RefMerchantResponse getMerchantById(String id) {
    	logger.info("Get Merchant for Id: {}", id);
    	
        try {
            RefMerchantResponse refMerchantResponse = merchantConnector.getMerchantById(id);
            setSystemDependentMerchants(refMerchantResponse);
            return refMerchantResponse;
        } catch (Exception ex) {
            logger.error("Exception in Get Merchant for Id: {}", id, ex);
            return exception("Merchant Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public RefMerchantResponse getMerchantsByUser(String username,
                                                  RefMerchantFilters refMerchantFilters) {
    	logger.info("Get Merchants By User: {} | {}", username, refMerchantFilters);
        RefMerchantResponse refMerchantResponse;

        try {
            refMerchantResponse = merchantConnector.getMerchantsByUser(username);
        } catch (Exception ex) {
            logger.error("Exception in Get Merchants By User: {} | {}", username, refMerchantFilters, ex);
            refMerchantResponse = exception("Merchants Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(refMerchantResponse.getRefMerchants())) {
            setSystemDependentMerchants(refMerchantResponse);
            refMerchantResponse.setRefMerchantsFilterList(getRefMerchantsFilterListByName(refMerchantResponse));

            if (refMerchantFilters != null) {
                TransactionResponse transactionResponse = null;
                if (refMerchantFilters.isNotUsedInTransactionsOnly()) {
                    transactionResponse = transactionService.getTransactionsByUser(username, null, false);
                }

                refMerchantResponse = applyFilters(refMerchantResponse, refMerchantFilters,
                        transactionResponse == null ? null : transactionResponse.getTransactions());
            }
        }

        return refMerchantResponse;
    }

    public CompletableFuture<RefMerchantResponse> getMerchantsByUserFuture(String username) {
        return CompletableFuture.supplyAsync(() -> getMerchantsByUser(username, null));
    }

    public RefMerchantResponse saveNewMerchant(RefMerchantRequest merchantRequest) {
    	logger.info("Save New Merchant: {}", merchantRequest);
    	
        try {
            return merchantConnector.saveNewMerchant(merchantRequest);
        } catch (Exception ex) {
            logger.error("Exception in Save New Merchant: {}", merchantRequest, ex);
            return exception("Save Merchant Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public RefMerchantResponse updateMerchant(String id, RefMerchantRequest merchantRequest) {
    	logger.info("Update Merchant: {}| {}", id, merchantRequest);
    	
        try {
            return merchantConnector.updateMerchant(id, merchantRequest);
        } catch (Exception ex) {
            logger.error("Exception in Update Merchant: {} | {}", id, merchantRequest, ex);
            return exception("Update Merchant Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public RefMerchantResponse deleteMerchant(String username, String id) {
    	logger.info("Delete Merchant: {}| {}", username, id);
        RefMerchantResponse refMerchantResponse;
        TransactionResponse transactionResponse = transactionService.getTransactionsByUser(username, null, false);

        if (transactionResponse.getStatus() == null) {
            Transaction usedTransaction = transactionResponse.getTransactions().stream()
                    .filter(transaction -> transaction.getRefMerchant().getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (usedTransaction == null) {
                try {
                    refMerchantResponse = merchantConnector.deleteMerchant(id);
                } catch (Exception ex) {
                    logger.error("Exception in Delete Merchant: {}", id, ex);
                    refMerchantResponse = exception("Delete Merchant Unavailable! Please Try Again!!!", ex.toString());
                }
            } else {
                logger.error("Delete Merchant Error, Merchant Used In Transactions: {} | {}", username, id);
                refMerchantResponse = exception("Delete Merchant Unavailable! Merchant is Used in Transactions!!!", null);
            }
        } else {
            logger.error("Delete Merchant Error, Empty Transactions List: {} | {}", username, id);
            refMerchantResponse = exception("Delete Merchant Unavailable! Error Retrieving Transactions!!!", null);
        }

        return refMerchantResponse;
    }

    private RefMerchantResponse exception(String errMsg, String message) {
        return RefMerchantResponse.builder()
                .refMerchants(emptyList())
                .deleteCount(0L)
                .refMerchantsFilterList(emptySet())
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
