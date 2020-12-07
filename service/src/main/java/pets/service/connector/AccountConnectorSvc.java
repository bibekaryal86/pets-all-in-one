package pets.service.connector;

import org.springframework.stereotype.Component;

import lombok.NonNull;
import pets.database.service.AccountServiceDb;
import pets.models.model.AccountRequest;
import pets.models.model.AccountResponse;

@Component
public class AccountConnectorSvc {
	
	private final AccountServiceDb accountServiceDb;
	
    public AccountConnectorSvc(AccountServiceDb accountServiceDb) {
    	this.accountServiceDb = accountServiceDb;
    }

    public AccountResponse getAccountById(@NonNull final String id) {
        return accountServiceDb.getAccountById(id);
    }

    public AccountResponse getAccountsByUser(@NonNull final String username) {
        return accountServiceDb.getAccountsByUsername(username);
    }

    public AccountResponse saveNewAccount(@NonNull final AccountRequest accountRequest) {
        return accountServiceDb.saveNewAccount(accountRequest);
    }

    public AccountResponse updateAccount(@NonNull final String id,
                                         @NonNull final AccountRequest accountRequest) {
        return accountServiceDb.updateAccountById(id, accountRequest);
    }

    public AccountResponse deleteAccount(@NonNull final String id) {
       return accountServiceDb.deleteAccountById(id);
    }
}
