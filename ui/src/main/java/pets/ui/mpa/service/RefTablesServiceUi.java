package pets.ui.mpa.service;

import static pets.ui.mpa.util.CommonUtils.removeApostropheForJavascript;
import static pets.ui.mpa.util.CommonUtils.toUppercase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.RefCategoryFilters;
import pets.models.model.RefMerchantFilters;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;
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
			return RefTablesModel.builder()
					.refAccountTypes(refTablesConnector.getRefAccountTypes().getRefAccountTypes())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Account Types"))
					.build();
		}
	}
	
	public RefTablesModel getRefBanks(String username) {
		try {
			return RefTablesModel.builder()
					.refBanks(refTablesConnector.getRefBanks().getRefBanks())
					.build();
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

			return RefTablesModel.builder()
					.refCategories(refTablesConnector.getRefCategories(username, refCategoryFilters).getRefCategories())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Categories"))
					.build();
		}
	}
	
	public RefTablesModel getRefCategoryTypes(String username, boolean usedInTxnsOnly) {
		try {
			return RefTablesModel.builder()
					.refCategoryTypes(refTablesConnector.getRefCategoryTypes(username, String.valueOf(usedInTxnsOnly))
							.getRefCategoryTypes())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Category Types"))
					.build();
		}
	}
	
	public RefTablesModel getRefMerchantById(String username, String merchantId) {
		try {
			return RefTablesModel.builder()
					.refMerchants(refTablesConnector.getRefMerchantById(username, merchantId).getRefMerchants())
					.build();
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

			return RefTablesModel.builder()
					.refMerchants(refMerchantResponse.getRefMerchants())
					.refMerchantsFilterList(refMerchantResponse.getRefMerchantsFilterList())
					.build();
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
			
			return RefTablesModel.builder()
					.refMerchants(
							refTablesConnector.updateRefMerchant(id, refMerchantRequest).getRefMerchants())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Update Ref Merchant"))
					.build();
		}
	}

	public RefTablesModel deleteRefMerchant(String username, String id) {
		try {
			return RefTablesModel.builder()
					.deleteCount(refTablesConnector.deleteRefMerchant(username, id).getDeleteCount().intValue())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Delete Ref Merchant"))
					.build();
		}
	}

	public RefTablesModel getRefTransactionTypes(String username) {
		try {
			return RefTablesModel.builder()
					.refTransactionTypes(refTablesConnector.getRefTransactionTypes().getRefTransactionTypes())
					.build();
		} catch (Exception ex) {
			return RefTablesModel.builder()
					.errMsg(errMsg(username, ex, "Get Ref Transaction Types"))
					.build();
		}
	}
	
	private String errMsg(String username, Exception ex, String methodName) {
		logger.error("Exception in {}: {}", methodName, username, ex);
		return String.format("Error in %s! Please Try Again!!!", methodName);
	}
}
