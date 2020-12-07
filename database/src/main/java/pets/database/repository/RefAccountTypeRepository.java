package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;
import static pets.database.utils.Constants.FIELD_NAME_ID;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.RefAccountTypeDto;

@Repository
public class RefAccountTypeRepository {
    private final MongoTemplate mongoTemplate;

    public RefAccountTypeRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<RefAccountTypeDto> getAllRefAccountTypes() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, FIELD_NAME_DESCRIPTION));
        return mongoTemplate.find(query, RefAccountTypeDto.class, COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS);
    }

    public RefAccountTypeDto getRefAccountTypeById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefAccountTypeDto.class,
                COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS);
    }

    public RefAccountTypeDto saveNewRefAccountType(RefAccountTypeDto refAccountType) {
        return mongoTemplate.save(refAccountType, COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS);
    }

    public long updateRefAccountTypeById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update,
                RefAccountTypeDto.class, COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS).getModifiedCount();
    }

    public long deleteRefAccountTypeById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefAccountTypeDto.class,
                COLLECTION_NAME_REF_ACCOUNT_TYPE_DETAILS).getDeletedCount();
    }
}
