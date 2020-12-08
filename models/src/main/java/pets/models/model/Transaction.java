package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction implements Serializable {
    private String id;
    private String description;
    private Account account;
    private Account trfAccount;
    private RefTransactionType refTransactionType;
    private RefCategory refCategory;
    private RefMerchant refMerchant;
    private User user;
    private String date;
    private BigDecimal amount;
    private Boolean regular;
    private Boolean necessary;
    private String creationDate;
    private String lastModified;
    private String accountFilter;
}
