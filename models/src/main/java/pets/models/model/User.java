package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private String id;
    private String username;
    @ToString.Exclude
    private String password;
    private String firstName;
    private String lastName;
    @ToString.Exclude
    private String streetAddress;
    private String city;
    private String state;
    @ToString.Exclude
    private String zipcode;
    @ToString.Exclude
    private String email;
    @ToString.Exclude
    private String phone;
    private String status;
    private String creationDate;
    private String lastModified;
}
