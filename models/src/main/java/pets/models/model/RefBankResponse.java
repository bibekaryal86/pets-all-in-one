package pets.models.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefBankResponse implements Serializable {
    private List<RefBank> refBanks;
    private Long deleteCount;
    private Status status;
}
