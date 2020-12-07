package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest implements Serializable {
    @NonNull
    private String typeId;
    @NonNull
    private String bankId;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal openingBalance;
    @NonNull
    private String status;
    @NonNull
    private String username;
}
