package pets.models.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCurrentBalances implements Serializable {
    private BigDecimal totalCash;
    private String accountTypeCashId;
    private BigDecimal totalCheckingAccounts;
    private String accountTypeCheckingAccountsId;
    private BigDecimal totalSavingsAccounts;
    private String accountTypeSavingsAccountsId;
    private BigDecimal totalInvestmentAccounts;
    private String accountTypeInvestmentAccountsId;
    private BigDecimal totalOtherDepositAccounts;
    private String accountTypeOtherDepositAccountsId;
    private BigDecimal totalCreditCards;
    private String accountTypeCreditCardsId;
    private BigDecimal totalLoansAndMortgages;
    private String accountTypeLoansAndMortgagesId;
    private BigDecimal totalOtherLoanAccounts;
    private String accountTypeOtherLoanAccountsId;
    private BigDecimal totalAssets;
    private BigDecimal totalDebts;
    private BigDecimal netWorth;
}
