package pets.service.service;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.RefBank;
import pets.models.model.RefBankResponse;
import pets.models.model.Status;
import pets.service.connector.RefBankConnectorSvc;

@Service
public class RefBankServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(RefBankServiceSvc.class);

    private final RefBankConnectorSvc bankConnector;

    public RefBankServiceSvc(RefBankConnectorSvc bankConnector) {
        this.bankConnector = bankConnector;
    }

    public RefBankResponse getAllBanks() {
    	logger.info("Get All Banks");
    	
        try {
            return bankConnector.getAllBanks();
        } catch (Exception ex) {
            logger.error("Exception in Get All Banks", ex);
            return exception("Banks Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    public RefBankResponse getBankById(String id) {
    	logger.info("Get Bank By Id: {}", id);
        RefBankResponse refBankResponse = getAllBanks();
        RefBank refBank;

        if (refBankResponse.getStatus() == null) {
            refBank = refBankResponse.getRefBanks().stream()
                    .filter(refBank1 -> refBank1.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (refBank == null) {
                logger.error("Bank Not Found for Id: {}", id);
                return exception("Bank Unavailable! Please Try Again!!!", null);
            } else {
                return RefBankResponse.builder()
                        .refBanks(singletonList(refBank))
                        .build();
            }
        } else {
            return refBankResponse;
        }
    }

    private RefBankResponse exception(String errMsg, String message) {
        return RefBankResponse.builder()
                .refBanks(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
