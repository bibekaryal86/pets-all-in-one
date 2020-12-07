package pets.database.model;

import static pets.database.utils.Constants.COLLECTION_NAME_TRANSACTION_DETAILS;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = COLLECTION_NAME_TRANSACTION_DETAILS)
public class TransactionDto implements Serializable {
    @Id
    private String id;
    private String description;
    private AccountDto account;
    private AccountDto trfAccount;
    private RefTransactionTypeDto refTransactionType;
    private RefCategoryDto refCategory;
    private RefMerchantDto refMerchant;
    private UserDto user;
    private String date;
    private BigDecimal amount;
    private Boolean regular;
    private Boolean necessary;
    private String creationDate;
    private String lastModified;
}
