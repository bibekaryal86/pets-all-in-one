package pets.ui.mpa.connector;

import org.springframework.stereotype.Component;

import pets.models.model.AccountFilters;
import pets.models.model.AccountRequest;
import pets.models.model.AccountResponse;
import pets.service.service.AccountServiceSvc;

@Component
public class AccountConnectorUi {
	
	private final AccountServiceSvc accountServiceSvc;

	public AccountConnectorUi(AccountServiceSvc accountServiceSvc) {
		this.accountServiceSvc = accountServiceSvc;
	}

	public AccountResponse getAccountById(String username, String id) {
		return accountServiceSvc.getAccountById(username, id, true);
	}

	public AccountResponse getAccountsByUsername(String username, AccountFilters accountFilters) {
		return accountServiceSvc.getAccountsByUser(username, accountFilters, true);
	}

	public AccountResponse saveNewAccount(String username, AccountRequest accountRequest) {
		return accountServiceSvc.saveNewAccount(username, accountRequest, true);
	}

	public AccountResponse updateAccount(String username, String id, AccountRequest accountRequest) {
		return accountServiceSvc.updateAccount(username, id, accountRequest, true);
	}

	public AccountResponse deleteAccount(String username, String id) {
		return accountServiceSvc.deleteAccount(username, id);
	}
}
