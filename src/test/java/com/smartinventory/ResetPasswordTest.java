package com.smartinventory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smartinventory.security.ConfirmationToken;
import com.smartinventory.security.ConfirmationTokenRepository;
import com.smartinventory.user.User;
import com.smartinventory.user.UserController;
import com.smartinventory.user.UserRepository;
import com.smartinventory.user.UserService;
import com.smartinventory.user.UserServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResetPasswordTest {
	@LocalServerPort
	private int port;


	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository users;

	@Mock
	private ConfirmationTokenRepository tokens;

	// @Autowired
	// private BCryptPasswordEncoder encoder;



	@Test
	public void sendEmail_Success() throws Exception {
		// URI uri = new URI(baseUrl + port + "/forgetPassword");

		User user = new User("geraldng1999@gmail.com", "geraldng", "password123");
	
		// ResponseEntity<User> 

		// userService.addUser(user1);
		Optional<User> userOptional = Optional.of(user);
		when(users.findByEmailIgnoreCase(user.getEmail())).thenReturn(userOptional);
		User resetuser = userService.forgetUserPassword(user);
		assertNotNull(resetuser);
		verify(users).findByEmailIgnoreCase(user.getEmail());
	}

	@Test
	public void verifyToken_Success(){
		ConfirmationToken tokenTest = new ConfirmationToken();
		tokenTest.setConfirmationToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b");

		Optional<ConfirmationToken> tokenOptional = Optional.of(tokenTest);
		User user = new User("geraldng1999@gmail.com", "geraldng", "password123");

		tokenOptional.get().setUser(user);
		Optional<User> userOptional = Optional.of(user);

		when(tokens.findByConfirmationToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b")).thenReturn(tokenOptional);
		when(users.findByEmailIgnoreCase("geraldng1999@gmail.com")).thenReturn(userOptional);

		User verifyUser = userService.validateResetToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b", "newpassword");
		assertNotNull(verifyUser);
		verify(tokens).findByConfirmationToken(tokenTest.getConfirmationToken());

	}
}
