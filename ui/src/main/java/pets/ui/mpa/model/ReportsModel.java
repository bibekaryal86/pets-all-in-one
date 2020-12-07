package pets.ui.mpa.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pets.models.model.ReportCashFlows;
import pets.models.model.ReportCategoryTypes;
import pets.models.model.ReportCurrentBalances;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportsModel implements Serializable {
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private String errMsg;
	private String userAction;
	
    private List<ReportCurrentBalances> reportCurrentBalances;
    private String selectedYear;
    private List<ReportCashFlows> reportCashFlows;
    private List<ReportCategoryTypes> reportCategoryTypes;
}
