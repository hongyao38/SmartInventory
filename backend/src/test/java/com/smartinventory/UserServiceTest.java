package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.smartinventory.exceptions.user.InvalidPasswordException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UsernameNotFoundException;
import com.smartinventory.exceptions.user.UsernameTakenException;
import com.smartinventory.security.token.ConfirmationTokenService;
import com.smartinventory.user.User;
import com.smartinventory.user.UserRepository;
import com.smartinventory.user.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository users;

    @Mock
    private ConfirmationTokenService tokenService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_NewUsername_SavedUser() {

        User user = new User("a@gmail.com", "user", "password");

        Optional<User> userOptional = Optional.empty();
        when(users.findByUsername(any(String.class))).thenReturn(userOptional);
        when(users.findByEmailIgnoreCase(any(String.class))).thenReturn(userOptional);

        String regUser = userService.registerUser(user);
        assertNotNull(regUser);

        verify(users).findByUsername(user.getUsername());
        verify(users).findByEmailIgnoreCase(user.getEmail());
    }

    @Test
    void addUser_SameUsername_ReturnException() {

        //arrange
        User user = new User("a@gmail.com", "user", "password");
        users.save(new User("a@gmail.com", "user", "password"));

        //mock
        Optional<User> userOptional = Optional.empty();
        when(users.findByUsername(any(String.class))).thenThrow(new UsernameTakenException(user));
        when(users.findByEmailIgnoreCase(any(String.class))).thenReturn(userOptional);

        try {
            String regUser = userService.registerUser(user);
        } catch (Exception e) {
            assertTrue(e instanceof UsernameTakenException);
        }
        

        try {
            verify(users).findByUsername(user.getUsername());
            verify(users).findByEmailIgnoreCase(user.getEmail());
        } catch (Exception e) {
            
        }
        
    }

    @Test
    void addUser_SameEmail_ReturnException() {

        //arrange
        User user = new User("a@gmail.com", "user", "password");
        users.save(new User("a@gmail.com", "user1", "password"));

        //mock
        when(users.findByEmailIgnoreCase(any(String.class))).thenThrow(new UserEmailTakenException(user.getEmail()));

        assertThrows(UserEmailTakenException.class, () -> userService.registerUser(user));
        

        verify(users).findByEmailIgnoreCase(user.getEmail());
    }

    @Test
    void loginUser_Success() {

            //arrange
            User user = new User("a@gmail.com", "user", "password");
            users.save(user);
            userService.enableUser(user.getEmail());

            //mock
            Optional<User> userOptional = Optional.of(user);
            when(users.findByUsername(any(String.class))).thenReturn(userOptional);

            //act
            ResponseEntity<String> loginUser = userService.loginUser(userOptional.get());

            //assert
            ResponseEntity<String> success = new ResponseEntity<>(String.format("%s: login success", user.getUsername()), HttpStatus.OK);
            assertEquals(success, loginUser);
            verify(users).findByUsername(user.getUsername());
    }

    @Test
    void loginUser_IncorrectUsername() {

            //arrange
            User user = new User("a@gmail.com", "user", "password");
            userService.registerUser(user);
            userService.enableUser(user.getEmail());
            User wrongUsername = new User("a@gmail.com", "user1", "password");

            //mock
            when(users.findByUsername(any(String.class))).thenThrow(new UsernameNotFoundException());

            //act
            assertThrows(UsernameNotFoundException.class, () -> userService.loginUser(wrongUsername));

            //assert
            verify(users).findByUsername(wrongUsername.getUsername());
    }

    @Test
    void loginUser_IncorrectPassword() {

            //arrange
            User user = new User("a@gmail.com", "user", "password");
            // userService.registerUser(user);
            users.save(user);
            userService.enableUser(user.getEmail());
            User incorrectUser = new User("a@gmail.com", "user", "wrongpassword");

            //mock
            Optional<User> userOptional = Optional.of(incorrectUser);
            when(users.findByUsername(any(String.class))).thenReturn(userOptional);
            // when(encoder.matches(user.getPassword(), userOptional.get().getPassword())).thenReturn(false);

            //act
            assertThrows(InvalidPasswordException.class, () -> userService.loginUser(incorrectUser));

            //assert
            verify(users).findByUsername(incorrectUser.getUsername());
    }
}
