package pets.ui.mpa.service;

import static org.springframework.util.StringUtils.hasText;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pets.models.model.UserResponse;
import pets.ui.mpa.connector.UserConnectorUi;;

@Service
public class UserServiceUi implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceUi.class);

	private final UserConnectorUi userConnector;

	public UserServiceUi(UserConnectorUi userConnector) {
		this.userConnector = userConnector;
	}

	public pets.models.model.User getUserByUsername(String username) {
		try {
			UserResponse userResponse = userConnector.getUserByUsername(username);
			
			if (userResponse.getStatus() != null && hasText(userResponse.getStatus().getErrMsg())) {
				logger.error("Error in Get User By Username: {} | {}", username, userResponse.getStatus());
				return null;
			} else {
				return userConnector.getUserByUsername(username).getUsers().get(0);
			}
		} catch (Exception ex) {
			logger.error("Exception in Get User By Username: {}", username, ex);
			return null;
		}
	}
	
	public CompletableFuture<pets.models.model.User> getUserByUsernameFuture(String username) {
		return CompletableFuture.supplyAsync(() -> getUserByUsername(username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		pets.models.model.User user = getUserByUsername(username);
		UserBuilder userBuilder = null;

		if (user == null) {
			throw new UsernameNotFoundException("User Not Found! Please Try Again!!!");
		} else {
			userBuilder = User.withUsername(username);
			userBuilder.password(user.getPassword());
			userBuilder.authorities("APP_USER");
		}

		return userBuilder.build();
	}
}
