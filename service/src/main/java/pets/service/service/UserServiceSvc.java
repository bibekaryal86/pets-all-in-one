package pets.service.service;

import static java.util.Collections.emptyList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pets.models.model.Status;
import pets.models.model.UserResponse;
import pets.service.connector.UserConnectorSvc;

@Service
public class UserServiceSvc {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceSvc.class);

    private final UserConnectorSvc userConnector;

    public UserServiceSvc(UserConnectorSvc userConnector) {
        this.userConnector = userConnector;
    }

    public UserResponse getUserByUsername(String username) {
    	logger.info("Get User by Username: {}", username);
        try {
            return userConnector.getUserByUsername(username);
        } catch (Exception ex) {
            logger.error("Exception in Get User by Username: {}", username, ex);
            return exception("User Unavailable! Please Try Again!!!", ex.toString());
        }
    }

    private UserResponse exception(String errMsg, String message) {
        return UserResponse.builder()
                .users(emptyList())
                .deleteCount(0L)
                .status(Status.builder()
                        .errMsg(errMsg)
                        .message(message)
                        .build())
                .build();
    }
}
