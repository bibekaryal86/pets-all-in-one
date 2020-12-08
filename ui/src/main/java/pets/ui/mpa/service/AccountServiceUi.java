package pets.ui.mpa.service;

import static org.springframework.util.StringUtils.hasText;
import static pets.ui.mpa.util.CommonUtils.formatAmountBalance;
import static pets.ui.mpa.util.CommonUtils.toUppercase;
import static pets.ui.mpa.util.ConstantUtils.ACCOUNT_TYPE_ID_CASH;
import static pets.ui.mpa.util.ConstantUtils.BANK_ID_CASH;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.Account;
import pets.models.model.AccountFilters;
import pets.models.model.AccountRequest;
import pets.models.model.AccountResponse;
import pets.models.model.Status;
import pets.ui.mpa.connector.AccountConnectorUi;
import pets.ui.mpa.model.AccountModel;

@Service
public class AccountServiceUi {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceUi.class);

	private final AccountConnectorUi accountConnector;

	public AccountServiceUi(AccountConnectorUi accountConnector) {
		this.accountConnector = accountConnector;
	}

	public AccountModel getAccountById(String username, String id) {
		try {
			AccountResponse accountResponse = accountConnector.getAccountById(username, id);
			
			if (accountResponse.getStatus() != null && hasText(accountResponse.getStatus().getErrMsg())) {
				return AccountModel.builder()
						.errMsg(errMsg(username, "Get Account by Id", accountResponse.getStatus()))
						.build();
			} else {
				return AccountModel.builder()
						.account(accountResponse.getAccounts().get(0))
						.build();
			}
		} catch (Exception ex) {
			return AccountModel.builder()
					.errMsg(errMsg(username, ex, "Get Account by Id"))
					.build();
		}
	}

	public AccountModel getAccountsByUsername(String username, AccountFilters accountFilters) {
		try {
			AccountResponse accountResponse = accountConnector.getAccountsByUsername(username, accountFilters);
			
			if (accountResponse.getStatus() != null && hasText(accountResponse.getStatus().getErrMsg())) {
				return AccountModel.builder()
						.errMsg(errMsg(username, "Get Accounts by Username", accountResponse.getStatus()))
						.build();
			} else {
				return AccountModel.builder()
						.accounts(accountResponse.getAccounts())
						.build();
			}
		} catch (Exception ex) {
			return AccountModel.builder()
					.errMsg(errMsg(username, ex, "Get Accounts by Username"))
					.build();
		}
	}
	
	public AccountModel saveNewAccount(String username, Account account) {
		try {
			AccountRequest accountRequest = AccountRequest.builder()
					.bankId(account.getRefBank().getId())
					.description(toUppercase(account.getDescription()))
					.openingBalance(formatAmountBalance(account.getOpeningBalance()))
					.status(account.getStatus())
					.typeId(account.getRefAccountType().getId())
					.username(username)
					.build();
			
			AccountResponse accountResponse = accountConnector.saveNewAccount(username, accountRequest);
			
			if (accountResponse.getStatus() != null && hasText(accountResponse.getStatus().getErrMsg())) {
				return AccountModel.builder()
						.errMsg(errMsg(username, "Save New Account", accountResponse.getStatus()))
						.build();
			} else {
				return AccountModel.builder()
						.account(accountResponse.getAccounts().get(0))
						.build();
			}
		} catch (Exception ex) {
			return AccountModel.builder()
					.errMsg(errMsg(username, ex, "Save New Account"))
					.build();
		}
	}

	public AccountModel updateAccount(String username, String id, Account account) {
		try {
			AccountRequest accountRequest = AccountRequest.builder()
					.bankId(account.getRefBank().getId())
					.description(toUppercase(account.getDescription()))
					.openingBalance(account.getOpeningBalance())
					.status(account.getStatus())
					.typeId(account.getRefAccountType().getId())
					.username(username)
					.build();

			AccountResponse accountResponse = accountConnector.updateAccount(username, id, accountRequest);
			
			if (accountResponse.getStatus() != null && hasText(accountResponse.getStatus().getErrMsg())) {
				return AccountModel.builder()
						.errMsg(errMsg(username, "Update Account", accountResponse.getStatus()))
						.build();
			} else {
				return AccountModel.builder()
						.account(accountResponse.getAccounts().get(0))
						.build();
			}
		} catch (Exception ex) {
			return AccountModel.builder()
					.errMsg(errMsg(username, ex, "Update Account"))
					.build();
		}
	}

	public AccountModel deleteAccount(String username, String id) {
		try {
			AccountResponse accountResponse = accountConnector.deleteAccount(username, id);
			
			if (accountResponse.getStatus() != null && hasText(accountResponse.getStatus().getErrMsg())) {
				return AccountModel.builder()
						.errMsg(errMsg(username, "Delete Account", accountResponse.getStatus()))
						.build();
			} else {
				return AccountModel.builder()
						.deleteCount(accountResponse.getDeleteCount().intValue())
						.build();
			}
		} catch (Exception ex) {
			return AccountModel.builder()
					.errMsg(errMsg(username, ex, "Delete Account"))
					.build();
		}
	}
	
	private String errMsg(String username, String methodName, Status status) {
		logger.error("Error in {}: {} | {}", username, methodName, status);
		return status.getErrMsg();
	}

	private String errMsg(String username, Exception ex, String methodName) {
		logger.error("Exception in {}: {}", username, methodName, ex);
		return String.format("Error in %s! Please Try Again!!!", methodName);
	}

	/**
	 * @param account
	 * @return account type cash can only be linked to bank type cash
	 */
	public static boolean isAccountTypeCompatibleWithBankType(Account account) {
		String accountTypeId = "";
		String bankId = "";

		if (account != null && account.getRefAccountType() != null && account.getRefAccountType().getId() != null) {
			accountTypeId = account.getRefAccountType().getId();
		}

		if (account != null && account.getRefBank() != null && account.getRefBank().getId() != null) {
			bankId = account.getRefBank().getId();
		}
		
		if (accountTypeId.equals(ACCOUNT_TYPE_ID_CASH)) {
			return bankId.equals(BANK_ID_CASH);
		} else if (bankId.equals(BANK_ID_CASH)) {
			return accountTypeId.equals(ACCOUNT_TYPE_ID_CASH);
		}
		
		return true;
	}
}
