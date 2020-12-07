package pets.service.connector;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import pets.database.service.RefBankServiceDb;
import pets.models.model.RefBankResponse;

@Component
public class RefBankConnectorSvc {
	
	private final RefBankServiceDb refBankServiceDb;

    public RefBankConnectorSvc(RefBankServiceDb refBankServiceDb) {
        this.refBankServiceDb = refBankServiceDb;
    }

    @Cacheable(value = "banks", unless = "#result==null")
    public RefBankResponse getAllBanks() {
        return refBankServiceDb.getAllRefBanks();
    }
}
