package pets.models.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefCategoryResponse implements Serializable {
    private List<RefCategory> refCategories;
    private Long deleteCount;
    private Status status;
}
