package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_TRANSACTION_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_ID;
import static pets.database.utils.Constants.FIELD_NAME_USERNAME;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.TransactionDto;

@Repository
public class TransactionRepository {
    private final MongoTemplate mongoTemplate;

    public TransactionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<TransactionDto> getAllTransactions() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "date"));
        return mongoTemplate.findAll(TransactionDto.class, COLLECTION_NAME_TRANSACTION_DETAILS);
    }

    public TransactionDto getTransactionById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), TransactionDto.class,
                COLLECTION_NAME_TRANSACTION_DETAILS);
    }

    public List<TransactionDto> getTransactionsByUser(String username) {
        return mongoTemplate.find(Query.query(Criteria.where("user." + FIELD_NAME_USERNAME).is(username))
                        .with(Sort.by(Sort.Direction.DESC, "date")), TransactionDto.class,
                COLLECTION_NAME_TRANSACTION_DETAILS);
    }

    public TransactionDto saveNewTransaction(TransactionDto transaction) {
        return mongoTemplate.save(transaction, COLLECTION_NAME_TRANSACTION_DETAILS);
    }

    public long updateTransactionById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update, TransactionDto.class,
                COLLECTION_NAME_TRANSACTION_DETAILS).getModifiedCount();
    }

    public long deleteTransactionById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), TransactionDto.class,
                COLLECTION_NAME_TRANSACTION_DETAILS).getDeletedCount();
    }

    public long deleteTransactionsByAccountId(String accountId) {
        return mongoTemplate.remove(Query.query(Criteria.where("account.id").is(accountId)), TransactionDto.class,
                COLLECTION_NAME_TRANSACTION_DETAILS).getDeletedCount();
    }
}
