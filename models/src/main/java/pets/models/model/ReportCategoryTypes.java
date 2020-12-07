package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCategoryTypes implements Serializable {
    private RefCategoryType refCategoryType;
    private BigDecimal totalRefCategoryType;
    private List<ReportCategories> reportCategories;
}
