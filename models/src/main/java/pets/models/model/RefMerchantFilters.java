package pets.models.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefMerchantFilters implements Serializable {
    private String firstChar;
    private boolean notUsedInTransactionsOnly;
}
