package pets.service.service;

import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;
import static pets.service.utils.AccountHelper.applyAllDetailsStatic;
import static pets.service.utils.AccountHelper.applyFilters;
import static pets.service.utils.AccountHelper.calculateCurrentBalanceStatic;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pets.models.model.AccountFilters;
import pets.models.model.AccountRequest;
import pets.models.model.AccountResponse;
import pets.models.model.RefAccountTypeResponse;
import pets.models.model.RefBankResponse;
import pets.models.model.Status;
import pets.models.model.TransactionResponse;
import pets.service.connector.AccountConnectorSvc;

@Service
public class AccountServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceSvc.class);
    private final AccountConnectorSvc accountConnector;
    private final RefAccountTypeServiceSvc accountTypeService;
    private final RefBankServiceSvc bankService;
    private TransactionServiceSvc transactionService;

    public AccountServiceSvc(AccountConnectorSvc accountConnector,
                          RefAccountTypeServiceSvc accountTypeService,
                          RefBankServiceSvc bankService) {
        this.accountConnector = accountConnector;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
    }

    // to avoid circular dependency
    // maybe add a new class like AccountHelperService to avoid it
    @Autowired
    public void setTransactionService(TransactionServiceSvc transactionService) {
        this.transactionService = transactionService;
    }

    public AccountResponse getAccountById(String username, String id, boolean applyAllDetails) {
    	logger.info("Get Account by Id: {} | {} | {}", username, id, applyAllDetails);
        AccountResponse accountResponse;

        try {
            accountResponse = accountConnector.getAccountById(id);
        } catch (Exception ex) {
            logger.error("Exception in Get Account by Id: {} | {} | {}", username, id, applyAllDetails, ex);
            accountResponse = exception("Account Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(accountResponse.getAccounts())) {
            if (applyAllDetails) {
                applyAllDetails(accountResponse);
            }

            calculateCurrentBalance(username, accountResponse);
        }

        return accountResponse;
    }

    public AccountResponse getAccountsByUser(String username,
                                             AccountFilters accountFilters,
                                             boolean applyAllDetails) {
    	logger.info("Get Accounts by User: {} | {} | {}", username, accountFilters, applyAllDetails);
        AccountResponse accountResponse;

        try {
            accountResponse = accountConnector.getAccountsByUser(username);
        } catch (Exception ex) {
            logger.error("Exception in Get Accounts By User: {} | {} | {}", username, accountFilters, applyAllDetails, ex);
            accountResponse = exception("Accounts Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(accountResponse.getAccounts())) {
            if (accountFilters != null) {
                accountResponse = applyFilters(accountResponse, accountFilters);
            }

            if (applyAllDetails) {
                applyAllDetails(accountResponse);
            }

            calculateCurrentBalance(username, accountResponse);
        }

        return accountResponse;
    }

    public CompletableFuture<AccountResponse> getAccountsByUserFuture(String username) {
        return CompletableFuture.supplyAsync(() -> getAccountsByUser(username, null, true));
    }

    public AccountResponse saveNewAccount(String username,
                                          AccountRequest accountRequest,
                                          boolean applyAllDetails) {
    	logger.info("Save New Account: {} | {} | {}", username, accountRequest, applyAllDetails);
        AccountResponse accountResponse;

        try {
            accountResponse = accountConnector.saveNewAccount(accountRequest);
        } catch (Exception ex) {
            logger.error("Exception in Save New Account: {} | {} | {}", username, accountRequest, applyAllDetails, ex);
            accountResponse = exception("Save Account Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(accountResponse.getAccounts())) {
            if (applyAllDetails) {
                applyAllDetails(accountResponse);
            }

            calculateCurrentBalance(username, accountResponse);
        }

        return accountResponse;
    }

    public AccountResponse updateAccount(String username, String id,
                                         AccountRequest accountRequest, boolean applyAllDetails) {
    	logger.info("Update Account: {} | {} | {} | {}", username, id, accountRequest, applyAllDetails);
        AccountResponse accountResponse;

        try {
            accountResponse = accountConnector.updateAccount(id, accountRequest);
        } catch (Exception ex) {
            logger.error("Exception in Update Account: {} | {} | {} | {}", username, id, accountRequest, applyAllDetails, ex);
            accountResponse = exception("Update Account Unavailable! Please Try Again!!!", ex.toString());
        }

        if (!isEmpty(accountResponse.getAccounts())) {
            if (applyAllDetails) {
                applyAllDetails(accountResponse);
            }

            calculateCurrentBalance(username, accountResponse);
        }

        return accountResponse;
    }

    public AccountResponse deleteAccount(String username, String id) {
    	logger.info("Delete Account: {} | {}", username, id);
        AccountResponse accountResponse;

        try {
            accountResponse = accountConnector.deleteAccount(id);

            if (accountResponse.getDeleteCount().intValue() > 0) {
                TransactionResponse transactionResponse = transactionService.deleteTransactionsByAccount(id);

                if (transactionResponse.getStatus() != null) {
                    accountResponse = exception("Account Deleted! But Error Deleting Transactions!! Please Try Again!!!", null);
                }
            }
        } catch (Exception ex) {
            logger.error("Exception in Delete Account: {} | {}", username, id, ex);
            accountResponse = exception("Delete Account Unavailable! Please Try Again!!!", ex.toString());
        }

        return accountResponse;
    }

    private void applyAllDetails(AccountResponse accountResponse) {
        RefAccountTypeResponse refAccountTypeResponse = accountTypeService.getAllAccountTypes();
        RefBankResponse refBankResponse = bankService.getAllBanks();
        applyAllDetailsStatic(accountResponse, refAccountTypeResponse.getRefAccountTypes(),
                refBankResponse.getRefBanks());
    }

    private void calculateCurrentBalance(String username, AccountResponse accountResponse) {
        TransactionResponse transactionResponse = transactionService.getTransactionsByUser(username, null, false);
        calculateCurrentBalanceStatic(accountResponse, transactionResponse.getTransactions());
    }

    private AccountResponse exception(String errMsg, String message) {
    	return AccountResponse.builder()
                .accounts(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
