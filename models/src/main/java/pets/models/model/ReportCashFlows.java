package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCashFlows implements Serializable {
    private String month;
    private int monthToSort;
    private String monthBeginDate;
    private String monthEndDate;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
    private BigDecimal netSavings;
}
