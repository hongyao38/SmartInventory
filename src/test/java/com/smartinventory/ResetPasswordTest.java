package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.security.ConfirmationToken;
import com.smartinventory.security.ConfirmationTokenRepository;
import com.smartinventory.user.User;
import com.smartinventory.user.UserRepository;
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
    public void sendEmail_Fail_UserEmailNotFound() {
        // Create new user 
        String email = "me@live.com.sg";
        User user = new User(email, "johndoe", "badpassword");

        // Exception is to be expected because mock repo does not have such entry
        assertThrows(UserEmailNotFoundException.class,
                    () -> userService.forgetUserPassword(user));
        
        verify(users).findByEmailIgnoreCase(email);
    }

    @Test
    public void verifyToken_Success() {
        // Create token for testing
        ConfirmationToken tokenTest = new ConfirmationToken();
        tokenTest.setConfirmationToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b");

        // Create user for testing
        Optional<ConfirmationToken> tokenOptional = Optional.of(tokenTest);
        User user = new User("geraldng1999@gmail.com", "geraldng", "password123");

        tokenOptional.get().setUser(user);

        // Mock return object for updateUserPassword a
        String email = user.getEmail();
        when(tokens.findByConfirmationToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b")).thenReturn(tokenOptional);
        when(users.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        when(userService.updateUserPassword(email, new User(email, "geraldng", "newPassword"))).thenReturn(user);

        // Assertion
        User verifyUser = userService.validateResetToken("d6bde77a-6efa-4a2d-86d5-a2aef368f03b", "newpassword");
        assertNotNull(verifyUser);
        verify(tokens).findByConfirmationToken(tokenTest.getConfirmationToken());
    }
}
