package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;
import static pets.database.utils.Constants.FIELD_NAME_ID;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.RefTransactionTypeDto;

@Repository
public class RefTransactionTypeRepository {
    private final MongoTemplate mongoTemplate;

    public RefTransactionTypeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<RefTransactionTypeDto> getAllRefTransactionTypes() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, FIELD_NAME_DESCRIPTION));
        return mongoTemplate.find(query, RefTransactionTypeDto.class, COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS);
    }

    public RefTransactionTypeDto getRefTransactionTypeById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefTransactionTypeDto.class,
                COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS);
    }

    public RefTransactionTypeDto saveNewRefTransactionType(RefTransactionTypeDto refTransactionType) {
        return mongoTemplate.save(refTransactionType, COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS);
    }

    public long updateRefTransactionTypeById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update,
                RefTransactionTypeDto.class, COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS).getModifiedCount();
    }

    public long deleteRefTransactionTypeById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefTransactionTypeDto.class,
                COLLECTION_NAME_REF_TRANSACTION_TYPE_DETAILS).getDeletedCount();
    }
}
