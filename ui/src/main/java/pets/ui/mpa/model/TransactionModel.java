package pets.ui.mpa.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pets.models.model.Transaction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionModel implements Serializable {
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private String errMsg;
	private String userAction;

	private List<Transaction> transactions;
	private Transaction transaction;
	private int deleteCount;

	private String newMerchant;
	private boolean fromAccountPage;
	private boolean fromMerchantPage;
}
