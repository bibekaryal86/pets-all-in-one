package pets.service.connector;

import org.springframework.stereotype.Component;

import lombok.NonNull;
import pets.database.service.UserServiceDb;
import pets.models.model.UserResponse;

@Component
public class UserConnectorSvc {
	
	private final UserServiceDb userServiceDb;

    public UserConnectorSvc(UserServiceDb userServiceDb) {
        this.userServiceDb = userServiceDb;
    }

    public UserResponse getUserByUsername(@NonNull final String username) {
        return userServiceDb.getUserByUsername(username);
    }
}
