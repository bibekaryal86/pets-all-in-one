package pets.database.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.util.StringUtils.hasText;
import static pets.database.utils.Commons.convertDtoToEntityTransactions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import pets.database.model.AccountDto;
import pets.database.model.RefCategoryDto;
import pets.database.model.RefMerchantDto;
import pets.database.model.RefTransactionTypeDto;
import pets.database.model.TransactionDto;
import pets.database.model.UserDto;
import pets.database.repository.TransactionRepository;
import pets.models.model.Status;
import pets.models.model.TransactionRequest;
import pets.models.model.TransactionResponse;

@Service
public class TransactionServiceDb {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceDb.class);

    private final TransactionRepository transactionDao;

    public TransactionServiceDb(TransactionRepository transactionDao) {
        this.transactionDao = transactionDao;
    }

    public TransactionResponse getAllTransactions() {
        logger.info("Before Get All Transactions");
        List<TransactionDto> transactions = new ArrayList<>();
        Status status = null;

        try {
            transactions = transactionDao.getAllTransactions();
        } catch (Exception ex) {
            logger.error("Get All Transactions", ex);
            status = Status.builder()
                    .errMsg("Error Retrieving All Transactions, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Get All Transactions: {}", transactions.size());
        return TransactionResponse.builder()
                .transactions(convertDtoToEntityTransactions(transactions))
                .status(status)
                .build();
    }

    public TransactionResponse getTransactionById(String id) {
        logger.info("Before Get Transaction By Id: {}", id);
        TransactionDto transaction = null;
        Status status = null;

        try {
            transaction = transactionDao.getTransactionById(id);
        } catch (Exception ex) {
            logger.error("Get Transaction By Id: {}", id, ex);
            status = Status.builder()
                    .errMsg("Error Retrieving Transaction, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Get Transaction By Id: {} | {}", id, transaction);
        return TransactionResponse.builder()
                .transactions(transaction == null ? emptyList() : convertDtoToEntityTransactions(singletonList(transaction)))
                .status(status)
                .build();
    }


    public TransactionResponse getTransactionsByUser(String username) {
        logger.info("Before Get Transactions By User: {}", username);
        List<TransactionDto> transactions = new ArrayList<>();
        Status status = null;

        try {
            transactions = transactionDao.getTransactionsByUser(username);
        } catch (Exception ex) {
            logger.error("Get Transaction By User: {}", username, ex);
            status = Status.builder()
                    .errMsg("Error Retrieving Transactions, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Get Transaction By User: {} | {}", username, transactions.size());
        return TransactionResponse.builder()
                .transactions(convertDtoToEntityTransactions(transactions))
                .status(status)
                .build();
    }

    public TransactionResponse saveNewTransaction(TransactionRequest transactionRequest) {
        logger.info("Before Save New Transaction: {}", transactionRequest);
        TransactionDto newTransaction;
        Status status = null;

        try {
            newTransaction = TransactionDto.builder()
                    .description(transactionRequest.getDescription())
                    .account(AccountDto.builder()
                            .id(transactionRequest.getAccountId())
                            .build())
                    .refTransactionType(RefTransactionTypeDto.builder()
                            .id(transactionRequest.getTypeId())
                            .build())
                    .refCategory(RefCategoryDto.builder()
                            .id(transactionRequest.getCategoryId())
                            .build())
                    .refMerchant(RefMerchantDto.builder()
                            .id(transactionRequest.getMerchantId())
                            .build())
                    .user(UserDto.builder()
                            .username(transactionRequest.getUsername())
                            .build())
                    .date(transactionRequest.getDate())
                    .amount(transactionRequest.getAmount())
                    .regular(transactionRequest.getRegular())
                    .necessary(transactionRequest.getNecessary())
                    .creationDate(LocalDate.now().toString())
                    .lastModified(LocalDateTime.now().toString())
                    .build();

            if (hasText(transactionRequest.getTrfAccountId())) {
                newTransaction = newTransaction.toBuilder()
                        .trfAccount(AccountDto.builder()
                                .id(transactionRequest.getTrfAccountId())
                                .build())
                        .build();
            }

            newTransaction = transactionDao.saveNewTransaction(newTransaction);

            if (!hasText(newTransaction.getId())) {
                newTransaction = null;
                status = Status.builder()
                        .errMsg("Error Saving Transaction, Please Try Again!!!")
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Save New Transaction: {}", transactionRequest, ex);
            newTransaction = null;
            status = Status.builder()
                    .errMsg("Error Saving Transaction, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Save New Transaction: {}", newTransaction);
        return TransactionResponse.builder()
                .transactions(newTransaction == null ? emptyList() : convertDtoToEntityTransactions(singletonList(newTransaction)))
                .status(status)
                .build();
    }

    public TransactionResponse updateTransactionById(String id, TransactionRequest transactionRequest) {
        logger.info("Before Update Transaction By Id: {} | {}", id, transactionRequest);
        TransactionResponse transactionResponse;
        Status status;

        try {
            Update update = new Update();

            if (transactionRequest.getDescription() != null) {
                update.set("description", transactionRequest.getDescription());
            }

            if (hasText(transactionRequest.getAccountId())) {
                update.set("account.id", transactionRequest.getAccountId());
            }
            if (hasText(transactionRequest.getTrfAccountId())) {
                update.set("trfAccount.id", transactionRequest.getTrfAccountId());
            }
            if (hasText(transactionRequest.getTypeId())) {
                update.set("refTransactionType.id", transactionRequest.getTypeId());
            }
            if (hasText(transactionRequest.getCategoryId())) {
                update.set("refCategory.id", transactionRequest.getCategoryId());
            }
            if (hasText(transactionRequest.getMerchantId())) {
                update.set("refMerchant.id", transactionRequest.getMerchantId());
            }
            if (hasText(transactionRequest.getDate())) {
                update.set("date", transactionRequest.getDate());
            }

            update.set("amount", transactionRequest.getAmount());
            update.set("regular", transactionRequest.getRegular());
            update.set("necessary", transactionRequest.getNecessary());

            update.set("lastModified", LocalDateTime.now().toString());

            long modifiedCount = transactionDao.updateTransactionById(id, update);

            if (modifiedCount > 0) {
                transactionResponse = getTransactionById(id);
            } else {
                status = Status.builder()
                        .errMsg("Error Updating Transaction, Please Try Again!!!")
                        .build();
                transactionResponse = TransactionResponse.builder()
                        .transactions(emptyList())
                        .status(status)
                        .build();
            }
        } catch (Exception ex) {
            logger.error("Update Transaction By Id: {} | {}", id, transactionRequest, ex);
            status = Status.builder()
                    .errMsg("Error Updating Transaction, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
            transactionResponse = TransactionResponse.builder()
                    .transactions(emptyList())
                    .status(status)
                    .build();
        }

        logger.info("After Update Transaction By Id: {} | transaction: {}", id, transactionResponse);
        return transactionResponse;
    }

    public TransactionResponse deleteTransactionById(String id) {
        logger.info("Before Delete Transaction By Id: {}", id);
        long deleteCount = 0;
        Status status = null;

        try {
            deleteCount = transactionDao.deleteTransactionById(id);
        } catch (Exception ex) {
            logger.error("Delete Transaction By Id: {}", id, ex);
            status = Status.builder()
                    .errMsg("Error Deleting Transaction, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Delete Transaction By Id: {} | deleteCount: {}", id, deleteCount);
        return TransactionResponse.builder()
                .transactions(emptyList())
                .deleteCount(deleteCount)
                .status(status)
                .build();
    }

    public TransactionResponse deleteTransactionsByAccountId(String accountId) {
        logger.info("Before Delete Transactions By Account Id: {}", accountId);
        long deleteCount = 0;
        Status status = null;

        try {
            deleteCount = transactionDao.deleteTransactionsByAccountId(accountId);
        } catch (Exception ex) {
            logger.error("Delete Transaction By Id: {}", accountId, ex);
            status = Status.builder()
                    .errMsg("Error Deleting Transactions, Please Try Again!!!")
                    .message(ex.toString())
                    .build();
        }

        logger.info("After Delete Transactions By Account Id: {} | deleteCount: {}", accountId, deleteCount);
        return TransactionResponse.builder()
                .transactions(emptyList())
                .deleteCount(deleteCount)
                .status(status)
                .build();
    }
}
