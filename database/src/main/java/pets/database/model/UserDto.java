package pets.database.model;

import static pets.database.utils.Constants.COLLECTION_NAME_USER_DETAILS;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = COLLECTION_NAME_USER_DETAILS)
public class UserDto implements Serializable {
    @Id
    private String id;
    private String username;
    @ToString.Exclude
    private String password;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @ToString.Exclude
    @Field(name = "street_address")
    private String streetAddress;
    private String city;
    private String state;
    @ToString.Exclude
    @Field(name = "zip_code")
    private String zipcode;
    @ToString.Exclude
    private String email;
    @ToString.Exclude
    private String phone;
    private String status;
    private String creationDate;
    private String lastModified;
}
