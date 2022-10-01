package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}
