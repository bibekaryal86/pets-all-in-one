package pets.database.model;

import static pets.database.utils.Constants.COLLECTION_NAME_ACCOUNT_DETAILS;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Document(collection = COLLECTION_NAME_ACCOUNT_DETAILS)
public class AccountDto implements Serializable {
    @Id
    private String id;
    private RefAccountTypeDto refAccountType;
    private RefBankDto refBank;
    private String description;
    private UserDto user;
    @Field(name = "opening_balance")
    private BigDecimal openingBalance;
    private String status;
    private String creationDate;
    private String lastModified;
}
