package pets.service.connector;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import pets.database.service.RefCategoryTypeServiceDb;
import pets.models.model.RefCategoryTypeResponse;

@Component
public class RefCategoryTypeConnectorSvc {
	
	private final RefCategoryTypeServiceDb refCategoryTypeServiceDb;

    public RefCategoryTypeConnectorSvc(RefCategoryTypeServiceDb refCategoryTypeServiceDb) {
        this.refCategoryTypeServiceDb = refCategoryTypeServiceDb;
    }

    @Cacheable(value = "categoryTypes", unless = "#result==null")
    public RefCategoryTypeResponse getAllCategoryTypes() {
        return refCategoryTypeServiceDb.getAllRefCategoryTypes();
    }
}
