package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefCategory implements Serializable {
    private String id;
    private String description;
    private RefCategoryType refCategoryType;
    private String creationDate;
    private String lastModified;
}