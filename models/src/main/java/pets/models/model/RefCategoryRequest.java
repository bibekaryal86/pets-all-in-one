package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefCategoryRequest implements Serializable {
    @NonNull
    private String description;
    @NonNull
    private String categoryTypeId;
}
