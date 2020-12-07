package pets.service.config;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import pets.service.service.RefAccountTypeServiceSvc;
import pets.service.service.RefBankServiceSvc;
import pets.service.service.RefCategoryServiceSvc;
import pets.service.service.RefTransactionTypeServiceSvc;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private RefAccountTypeServiceSvc accountTypeService;
    @Autowired
    private RefBankServiceSvc bankService;
    @Autowired
    private RefCategoryServiceSvc categoryService;
    @Autowired
    private RefTransactionTypeServiceSvc transactionTypeService;
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("accountTypes", "banks", "categories", "categoryTypes", "transactionTypes");
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    private void putAllCache() throws InterruptedException {
        logger.info("Firing Cache Evict!!!");
        cacheManager().getCacheNames()
                .forEach(cacheName -> requireNonNull(cacheManager().getCache(cacheName)).clear());

        Thread.sleep(5000);

        logger.info("Firing All Cache!!!");
        accountTypeService.getAllAccountTypes();
        bankService.getAllBanks();
        categoryService.getAllCategories(null, null);
        // category type cache is enabled when calling get all categories
        transactionTypeService.getAllTransactionTypes();
        logger.info("Finish Put All Cache!!!");
    }
}
