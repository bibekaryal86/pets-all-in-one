package pets.models.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportsResponse implements Serializable {
    private List<ReportCurrentBalances> reportCurrentBalances;
    private List<ReportCashFlows> reportCashFlows;
    private List<ReportCategoryTypes> reportCategoryTypes;
    private Status status;
}
