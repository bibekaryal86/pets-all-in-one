package pets.ui.mpa.connector;

import org.springframework.stereotype.Component;

import pets.models.model.RefAccountTypeResponse;
import pets.models.model.RefBankResponse;
import pets.models.model.RefCategoryFilters;
import pets.models.model.RefCategoryResponse;
import pets.models.model.RefCategoryTypeResponse;
import pets.models.model.RefMerchantFilters;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;
import pets.models.model.RefTransactionTypeResponse;
import pets.service.service.RefAccountTypeServiceSvc;
import pets.service.service.RefBankServiceSvc;
import pets.service.service.RefCategoryServiceSvc;
import pets.service.service.RefCategoryTypeServiceSvc;
import pets.service.service.RefMerchantServiceSvc;
import pets.service.service.RefTransactionTypeServiceSvc;

@Component
public class RefTablesConnectorUi {
	
	private final RefAccountTypeServiceSvc refAccountTypeServiceSvc;
	private final RefBankServiceSvc refBankServiceSvc;
	private final RefCategoryServiceSvc refCategoryServiceSvc;
	private final RefCategoryTypeServiceSvc refCategoryTypeServiceSvc;
	private final RefMerchantServiceSvc refMerchantServiceSvc;
	private final RefTransactionTypeServiceSvc refTransactionTypeServiceSvc;

	public RefTablesConnectorUi(RefAccountTypeServiceSvc refAccountTypeServiceSvc, RefBankServiceSvc refBankServiceSvc,
			RefCategoryServiceSvc refCategoryServiceSvc, RefCategoryTypeServiceSvc refCategoryTypeServiceSvc, 
			RefMerchantServiceSvc refMerchantServiceSvc, RefTransactionTypeServiceSvc refTransactionTypeServiceSvc) {
		this.refAccountTypeServiceSvc = refAccountTypeServiceSvc;
		this.refBankServiceSvc = refBankServiceSvc;
		this.refCategoryServiceSvc = refCategoryServiceSvc;
		this.refCategoryTypeServiceSvc = refCategoryTypeServiceSvc;
		this.refMerchantServiceSvc = refMerchantServiceSvc;
		this.refTransactionTypeServiceSvc = refTransactionTypeServiceSvc;
	}

	public RefAccountTypeResponse getRefAccountTypes() {
		return refAccountTypeServiceSvc.getAllAccountTypes();
	}
	
	public RefBankResponse getRefBanks() {
		return refBankServiceSvc.getAllBanks();
	}
	
	public RefCategoryResponse getRefCategories(String username, RefCategoryFilters refCategoryFilters) {
		return refCategoryServiceSvc.getAllCategories(username, refCategoryFilters);
	}
	
	public RefCategoryTypeResponse getRefCategoryTypes(String username, String usedInTxnsOnly) {
		return refCategoryTypeServiceSvc.getAllCategoryTypes(username, Boolean.valueOf(usedInTxnsOnly));
	}
	
	public RefMerchantResponse getRefMerchantById(String username, String id) {
		return refMerchantServiceSvc.getMerchantById(id);
	}

	public RefMerchantResponse getRefMerchantsByUsername(String username, RefMerchantFilters refMerchantFilters) {
		return refMerchantServiceSvc.getMerchantsByUser(username, refMerchantFilters);
	}
	
	public RefMerchantResponse updateRefMerchant(String id, RefMerchantRequest refMerchantRequest) {
		return refMerchantServiceSvc.updateMerchant(id, refMerchantRequest);
	}

	public RefMerchantResponse deleteRefMerchant(String username, String id) {
		return refMerchantServiceSvc.deleteMerchant(username, id);
	}

	public RefTransactionTypeResponse getRefTransactionTypes() {
		return refTransactionTypeServiceSvc.getAllTransactionTypes();
	}
}
