package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account implements Serializable {
    private String id;
    private RefAccountType refAccountType;
    private RefBank refBank;
    private String description;
    private User user;
    private BigDecimal openingBalance;
    private String status;
    private String creationDate;
    private String lastModified;
    private BigDecimal currentBalance;
}
