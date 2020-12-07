package pets.database.model;

import static pets.database.utils.Constants.COLLECTION_NAME_REF_BANK_DETAILS;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = COLLECTION_NAME_REF_BANK_DETAILS)
public class RefBankDto implements Serializable {
    @Id
    private String id;
    private String description;
    private String creationDate;
    private String lastModified;
}
