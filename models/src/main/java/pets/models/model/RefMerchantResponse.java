package pets.models.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefMerchantResponse implements Serializable {
    private List<RefMerchant> refMerchants;
    private Long deleteCount;
    private Status status;
    private Set<String> refMerchantsFilterList;
}
