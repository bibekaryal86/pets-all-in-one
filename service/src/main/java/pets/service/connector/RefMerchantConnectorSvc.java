package pets.service.connector;

import org.springframework.stereotype.Component;

import lombok.NonNull;
import pets.database.service.RefMerchantServiceDb;
import pets.models.model.RefMerchantRequest;
import pets.models.model.RefMerchantResponse;

@Component
public class RefMerchantConnectorSvc {
	
	private final RefMerchantServiceDb refMerchantServiceDb;

    public RefMerchantConnectorSvc(RefMerchantServiceDb refMerchantServiceDb) {
        this.refMerchantServiceDb = refMerchantServiceDb;
    }

    public RefMerchantResponse getMerchantById(@NonNull final String id) {
        return refMerchantServiceDb.getRefMerchantById(id);
    }

    public RefMerchantResponse getMerchantsByUser(@NonNull final String username) {
        return refMerchantServiceDb.getAllRefMerchantsByUsername(username);
    }

    public RefMerchantResponse saveNewMerchant(@NonNull final RefMerchantRequest merchantRequest) {
        return refMerchantServiceDb.saveNewRefMerchant(merchantRequest);
    }

    public RefMerchantResponse updateMerchant(@NonNull final String id,
                                              @NonNull final RefMerchantRequest merchantRequest) {
        return refMerchantServiceDb.updateRefMerchantById(id, merchantRequest);
    }

    public RefMerchantResponse deleteMerchant(@NonNull final String id) {
        return refMerchantServiceDb.deleteRefMerchantById(id);
    }
}
