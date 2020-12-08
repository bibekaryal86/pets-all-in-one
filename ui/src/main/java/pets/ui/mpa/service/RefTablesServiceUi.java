package pets.ui.mpa.service;

import static org.springframework.util.StringUtils.hasText;
import static pets.ui.mpa.util.CommonUtils.removeApostropheForJavascript;
import static pets.ui.mpa.util.CommonUtils.toUppercase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.RefAccountTypeResponse;
import pets.models.model.RefBankResponse;
import pets.models.model.RefCategoryFilters;
import pets.models.model.RefCategoryResponse;
import pets.models.model.RefCategoryTypeResponse;
import pets.models.model.RefMerchantFilters;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;
import pets.models.model.RefTransactionTypeResponse;
import pets.models.model.Status;
import pets.ui.mpa.connector.RefTablesConnectorUi;
import pets.ui.mpa.model.RefTablesModel;

@Service
public class RefTablesServiceUi {

	private static final Logger logger = LoggerFactory.getLogger(RefTablesServiceUi.class);

	private final RefTablesConnectorUi refTablesConnector;

	public RefTablesServiceUi(RefTablesConnectorUi refTablesConnector) {
		this.refTablesConnector = refTablesConnector;
	}

	public RefTablesModel getRefAccountTypes(String username) {
		try {
			RefAccountTypeResponse refAccountTypeResponse = refTablesConnector.getRefAccountTypes();
			
			if (refAccountTypeResponse.getStatus() != null && hasText(refAccountTypeResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Account Types", refAccountTypeResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refAccountTypes(refAccountTypeResponse.getRefAccountTypes())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Account Types"))
					.build();
		}
	}
	
	public RefTablesModel getRefBanks(String username) {
		try {
			RefBankResponse refBankResponse = refTablesConnector.getRefBanks();
			
			if (refBankResponse.getStatus() != null && hasText(refBankResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Banks", refBankResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refBanks(refBankResponse.getRefBanks())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Banks"))
					.build();
		}
	}
	
	public RefTablesModel getRefCategories(String username, String refCategoryTypeId, boolean usedInTxnsOnly) {
		try {
			RefCategoryFilters refCategoryFilters = null;
			if (!"".equals(refCategoryTypeId)) {
				refCategoryFilters = RefCategoryFilters.builder()
						.categoryTypeId(refCategoryTypeId)
						.usedInTxnsOnly(usedInTxnsOnly)
						.build();
			} else if (usedInTxnsOnly) {
				refCategoryFilters = RefCategoryFilters.builder()
						.usedInTxnsOnly(usedInTxnsOnly)
						.build();
			}
			
			RefCategoryResponse refCategoryResponse = refTablesConnector.getRefCategories(username, refCategoryFilters);
			
			if (refCategoryResponse.getStatus() != null && hasText(refCategoryResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Categories", refCategoryResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refCategories(refCategoryResponse.getRefCategories())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Categories"))
					.build();
		}
	}
	
	public RefTablesModel getRefCategoryTypes(String username, boolean usedInTxnsOnly) {
		try {
			RefCategoryTypeResponse refCategoryTypeResponse = refTablesConnector.getRefCategoryTypes(username, String.valueOf(usedInTxnsOnly));
			
			if (refCategoryTypeResponse.getStatus() != null && hasText(refCategoryTypeResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Category Types", refCategoryTypeResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refCategoryTypes(refCategoryTypeResponse.getRefCategoryTypes())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Category Types"))
					.build();
		}
	}
	
	public RefTablesModel getRefMerchantById(String username, String merchantId) {
		try {
			RefMerchantResponse refMerchantResponse = refTablesConnector.getRefMerchantById(username, merchantId);
			
			if (refMerchantResponse.getStatus() != null && hasText(refMerchantResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Merchant By Id", refMerchantResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refMerchants(refMerchantResponse.getRefMerchants())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Merchant By Id"))
					.build();
		}
	}

	public RefTablesModel getRefMerchants(String username, String merchantNameBeginsWith) {
		try {
			RefMerchantFilters refMerchantFilters = null;
			if (!"ALL".equals(merchantNameBeginsWith)) {
				refMerchantFilters = RefMerchantFilters.builder()
						.firstChar(merchantNameBeginsWith)
						.notUsedInTransactionsOnly("0".equals(merchantNameBeginsWith))
						.build();
			}

			RefMerchantResponse refMerchantResponse = refTablesConnector.getRefMerchantsByUsername(username, refMerchantFilters);
			
			if (refMerchantResponse.getStatus() != null && hasText(refMerchantResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Merchants", refMerchantResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refMerchants(refMerchantResponse.getRefMerchants())
						.refMerchantsFilterList(refMerchantResponse.getRefMerchantsFilterList())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Merchants"))
					.build();
		}
	}
	
	public RefTablesModel updateRefMerchant(String username, String id, String description) {
		try {
			RefMerchantRequest refMerchantRequest = RefMerchantRequest.builder()
					.username(username)
					.description(toUppercase(removeApostropheForJavascript(description)))
					.build();
			
			RefMerchantResponse refMerchantResponse = refTablesConnector.updateRefMerchant(id, refMerchantRequest);
			
			if (refMerchantResponse.getStatus() != null && hasText(refMerchantResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Update Ref Merchant", refMerchantResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refMerchants(refMerchantResponse.getRefMerchants())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Update Ref Merchant"))
					.build();
		}
	}

	public RefTablesModel deleteRefMerchant(String username, String id) {
		try {
			RefMerchantResponse refMerchantResponse = refTablesConnector.deleteRefMerchant(username, id);
			
			if (refMerchantResponse.getStatus() != null && hasText(refMerchantResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Delete Ref Merchant", refMerchantResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.deleteCount(refMerchantResponse.getDeleteCount().intValue())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Delete Ref Merchant"))
					.build();
		}
	}

	public RefTablesModel getRefTransactionTypes(String username) {
		try {
			RefTransactionTypeResponse refTransactionTypeResponse = refTablesConnector.getRefTransactionTypes();
			
			if (refTransactionTypeResponse.getStatus() != null && hasText(refTransactionTypeResponse.getStatus().getErrMsg())) {
				return RefTablesModel.builder()
						.errMsg(errMsg(username, "Get Ref Transaction Types", refTransactionTypeResponse.getStatus()))
						.build();
			} else {
				return RefTablesModel.builder()
						.refTransactionTypes(refTransactionTypeResponse.getRefTransactionTypes())
						.build();
			}
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Transaction Types"))
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
}
