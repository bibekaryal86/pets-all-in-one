package pets.database.repository;

import static org.springframework.util.StringUtils.hasText;
import static pets.database.utils.Constants.COLLECTION_NAME_USER_DETAILS;
import static pets.database.utils.Constants.FIELD_NAME_ID;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import pets.database.model.UserDto;

@Repository
public class UserRepository {
    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserDto> getAllUsers() {
        return mongoTemplate.findAll(UserDto.class);
    }

    public UserDto findUserById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), UserDto.class);
    }

    public UserDto findUserByUsername(String username) {
        return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), UserDto.class);
    }

    public UserDto findByEmailOrPhone(String email, String phone) {
        if (hasText(email)) {
            return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), UserDto.class);
        } else {
            return mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), UserDto.class);
        }
    }

    public UserDto saveNewUser(UserDto newUser) {
        return mongoTemplate.save(newUser, COLLECTION_NAME_USER_DETAILS);
    }

    public long updateUserById(String id, Update update) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), update, UserDto.class)
                .getModifiedCount();
    }

    public long deleteUserById(String id) {
        return mongoTemplate.remove(Query.query(Criteria.where(FIELD_NAME_ID).is(id)), UserDto.class).getDeletedCount();
    }
}
