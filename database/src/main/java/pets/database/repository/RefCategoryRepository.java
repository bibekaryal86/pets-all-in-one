package pets.database.repository;

import static pets.database.utils.Constants.COLLECTION_NAME_REF_CATEGORY_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_DESCRIPTION;
import static pets.database.utils.Constants.FIELD_NAME_ID;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.RefCategoryDto;

@Repository
public class RefCategoryRepository {
    private final MongoTemplate mongoTemplate;

    public RefCategoryRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<RefCategoryDto> getAllRefCategories() {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.asc("refCategoryType." + FIELD_NAME_DESCRIPTION)));
        return mongoTemplate.find(query, RefCategoryDto.class, COLLECTION_NAME_REF_CATEGORY_DETAILS);
    }

    public RefCategoryDto getRefCategoryById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefCategoryDto.class,
                COLLECTION_NAME_REF_CATEGORY_DETAILS);
    }

    public RefCategoryDto saveNewRefCategory(RefCategoryDto refAccountType) {
        return mongoTemplate.save(refAccountType, COLLECTION_NAME_REF_CATEGORY_DETAILS);
    }

    public long updateRefCategoryById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update, RefCategoryDto.class,
                COLLECTION_NAME_REF_CATEGORY_DETAILS).getModifiedCount();
    }

    public long deleteRefCategoryById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), RefCategoryDto.class,
                COLLECTION_NAME_REF_CATEGORY_DETAILS).getDeletedCount();
    }
}
