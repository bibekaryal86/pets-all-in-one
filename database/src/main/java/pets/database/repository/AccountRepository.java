package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_ACCOUNT_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;
import static pets.database.utils.Constants.FIELD_NAME_ID;
import static pets.database.utils.Constants.FIELD_NAME_USERNAME;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.AccountDto;

@Repository
public class AccountRepository {
    private final MongoTemplate mongoTemplate;

    public AccountRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AccountDto> getAllAccounts() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, FIELD_NAME_DESCRIPTION));
        return mongoTemplate.find(query, AccountDto.class, COLLECTION_NAME_ACCOUNT_DETAILS);
    }

    public AccountDto getAccountById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), AccountDto.class,
                COLLECTION_NAME_ACCOUNT_DETAILS);
    }

    public List<AccountDto> getAllAccountsByUsername(String username) {
        return mongoTemplate.find(Query.query(Criteria.where("user." + FIELD_NAME_USERNAME)
                        .is(username)).with(Sort.by(Sort.Direction.ASC, FIELD_NAME_DESCRIPTION)), AccountDto.class,
                COLLECTION_NAME_ACCOUNT_DETAILS);
    }

    public AccountDto saveNewAccount(AccountDto account) {
        return mongoTemplate.save(account, COLLECTION_NAME_ACCOUNT_DETAILS);
    }

    public long updateAccountById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update, AccountDto.class,
                COLLECTION_NAME_ACCOUNT_DETAILS).getModifiedCount();
    }

    public long deleteAccountById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), AccountDto.class,
                COLLECTION_NAME_ACCOUNT_DETAILS).getDeletedCount();
    }
}
