package pets.ui.mpa.connector;

import org.springframework.stereotype.Component;

import pets.models.model.UserResponse;
import pets.service.service.UserServiceSvc;

@Component
public class UserConnectorUi {
	
	private final UserServiceSvc userServiceSvc;

	public UserConnectorUi(UserServiceSvc userServiceSvc) {
		this.userServiceSvc = userServiceSvc;
	}

	public UserResponse getUserByUsername(String username) {
		return userServiceSvc.getUserByUsername(username);
	}
}
