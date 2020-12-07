package pets.service.connector;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import pets.database.service.RefCategoryServiceDb;
import pets.models.model.RefCategoryResponse;

@Component
public class RefCategoryConnectorSvc {
	
	private final RefCategoryServiceDb refCategoryServiceDb;

    public RefCategoryConnectorSvc(RefCategoryServiceDb refCategoryServiceDb) {
        this.refCategoryServiceDb = refCategoryServiceDb;
    }

    @Cacheable(value = "categories", unless = "#result==null")
    public RefCategoryResponse getAllCategories() {
        return refCategoryServiceDb.getAllRefCategories();
    }
}
