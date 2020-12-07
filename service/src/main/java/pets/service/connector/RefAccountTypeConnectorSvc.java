package pets.service.connector;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import pets.database.service.RefAccountTypeServiceDb;
import pets.models.model.RefAccountTypeResponse;

@Component
public class RefAccountTypeConnectorSvc {
	
	private final RefAccountTypeServiceDb refAccountTypeServiceDb;
	
    public RefAccountTypeConnectorSvc(RefAccountTypeServiceDb refAccountTypeServiceDb) {
        this.refAccountTypeServiceDb = refAccountTypeServiceDb;
    }

    @Cacheable(value = "accountTypes", unless = "#result==null")
    public RefAccountTypeResponse getAllAccountTypes() {
        return refAccountTypeServiceDb.getAllRefAccountTypes();
    }
}
