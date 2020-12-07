package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest implements Serializable {
    @NonNull
    private String username;
    @NonNull
    @ToString.Exclude
    private String password;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @ToString.Exclude
    private String streetAddress;
    private String city;
    private String state;
    @ToString.Exclude
    private String zipcode;
    @ToString.Exclude
    @NonNull
    private String email;
    @ToString.Exclude
    @NonNull
    private String phone;
    @NonNull
    private String status;
}
