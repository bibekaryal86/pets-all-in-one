package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.RefAccountType;
import pets.models.model.RefAccountTypeResponse;
import pets.models.model.Status;
import pets.service.connector.RefAccountTypeConnectorSvc;

@Service
public class RefAccountTypeServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefAccountTypeServiceSvc.class);

    private final RefAccountTypeConnectorSvc accountTypeConnector;

    public RefAccountTypeServiceSvc(RefAccountTypeConnectorSvc accountTypeConnector) {
        this.accountTypeConnector = accountTypeConnector;
    }

    public RefAccountTypeResponse getAllAccountTypes() {
    	logger.info("Get All Account Types");
    	
        try {
            return accountTypeConnector.getAllAccountTypes();
        } catch (Exception ex) {
            logger.error("Exception in Get All Account Types", ex);
            return exception("Account Types Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public RefAccountTypeResponse getAccountTypeById(String id) {
    	logger.info("Get Account Type By Id: {}", id);
        RefAccountTypeResponse refAccountTypeResponse = getAllAccountTypes();
        RefAccountType accountType;

        if (refAccountTypeResponse.getStatus() == null) {
            accountType = refAccountTypeResponse.getRefAccountTypes().stream()
                    .filter(refAccountType -> refAccountType.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (accountType == null) {
                logger.error("Account Type Not Found for Id: {}", id);
                return exception("Account Type Unavailable! Please Try Again!!!", null);
            } else {
                return RefAccountTypeResponse.builder()
                        .refAccountTypes(singletonList(accountType))
                        .build();
            }
        } else {
            return refAccountTypeResponse;
        }
    }

    private RefAccountTypeResponse exception(String errMsg, String message) {
    	return RefAccountTypeResponse.builder()
                .refAccountTypes(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
