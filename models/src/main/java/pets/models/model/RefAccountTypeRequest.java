package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefAccountTypeRequest implements Serializable {
    @NonNull
    private String description;

    @JsonCreator
    public RefAccountTypeRequest(@JsonProperty("description") String description) {
        this.description = description;
    }
}
