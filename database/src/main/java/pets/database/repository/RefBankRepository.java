package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_REF_BANK_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;
import static pets.database.utils.Constants.FIELD_NAME_ID;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.RefBankDto;

@Repository
public class RefBankRepository {
    private final MongoTemplate mongoTemplate;

    public RefBankRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<RefBankDto> getAllRefBanks() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, FIELD_NAME_DESCRIPTION));
        return mongoTemplate.find(query, RefBankDto.class, COLLECTION_NAME_REF_BANK_DETAILS);
    }

    public RefBankDto getRefBankById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefBankDto.class,
                COLLECTION_NAME_REF_BANK_DETAILS);
    }

    public RefBankDto saveNewRefBank(RefBankDto refAccountType) {
        return mongoTemplate.save(refAccountType, COLLECTION_NAME_REF_BANK_DETAILS);
    }

    public long updateRefBankById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update, RefBankDto.class,
                COLLECTION_NAME_REF_BANK_DETAILS).getModifiedCount();
    }

    public long deleteRefBankById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefBankDto.class,
                COLLECTION_NAME_REF_BANK_DETAILS).getDeletedCount();
    }
}
