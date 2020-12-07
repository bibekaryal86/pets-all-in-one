package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCategories implements Serializable {
    private RefCategory refCategory;
    private BigDecimal totalRefCategory;
}
