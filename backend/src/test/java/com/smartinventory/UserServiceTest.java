package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.Invocation;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.appuser.AppUserService;
import com.smartinventory.exceptions.user.InvalidCredentialsException;
import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UsernameTakenException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;
import com.smartinventory.security.token.ConfirmationTokenService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AppUserRepository users;

    @Mock
    private ConfirmationTokenService tokenService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private AppUserService userService;

    @Test
    void addUser_NewUsername_SavedUser() {

        AppUser user = new AppUser("a@gmail.com", "user", "password");

        Optional<AppUser> userOptional = Optional.empty();
        when(users.findByUsername(any(String.class))).thenReturn(userOptional);
        when(users.findByEmailIgnoreCase(any(String.class))).thenReturn(userOptional);

        String regUser = userService.registerUser(user);
        assertNotNull(regUser);

        verify(users).findByUsername(user.getUsername());
        verify(users).findByEmailIgnoreCase(user.getEmail());
    }

    @Test
    void addUser_SameUsername_ReturnException() {

        // arrange
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        users.save(new AppUser("a@gmail.com", "user", "password"));

        // mock
        Optional<AppUser> userOptional = Optional.empty();
        when(users.findByUsername(any(String.class))).thenThrow(new UsernameTakenException());
        when(users.findByEmailIgnoreCase(any(String.class))).thenReturn(userOptional);

        try {
            userService.registerUser(user);
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

        // arrange
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        users.save(new AppUser("a@gmail.com", "user1", "password"));

        // mock
        when(users.findByEmailIgnoreCase(any(String.class))).thenThrow(new UserEmailTakenException());

        assertThrows(UserEmailTakenException.class, () -> userService.registerUser(user));

        verify(users).findByEmailIgnoreCase(user.getEmail());
    }

    // @Test
    // void loginUser_Success() {

    // //arrange
    // AppUser user = new AppUser("a@gmail.com", "user", "password");
    // users.save(user);
    // userService.enableUser(user.getEmail());

    // //mock
    // Optional<AppUser> userOptional = Optional.of(user);
    // when(users.findByUsername(any(String.class))).thenReturn(userOptional);

    // //act
    // ResponseEntity<JwtDTO> loginUser = userService.loginUser(userOptional.get());

    // //assert
    // ResponseEntity<String> success = new ResponseEntity<>(String.format("%s:
    // login success", user.getUsername()), HttpStatus.OK);
    // assertEquals(success, loginUser);
    // verify(users).findByUsername(user.getUsername());
    // }

    @Test
    void loginUser_IncorrectUsername() {

        // arrange
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        userService.registerUser(user);
        userService.enableUser(user.getEmail());
        AppUser wrongUsername = new AppUser("a@gmail.com", "user1", "password");

        // mock
        when(users.findByUsername(any(String.class))).thenThrow(new InvalidCredentialsException());

        // act
        assertThrows(InvalidCredentialsException.class,
                () -> userService.loginUser(wrongUsername.getUsername(), wrongUsername.getPassword()));

        // assert
        verify(users).findByUsername(wrongUsername.getUsername());
    }

    @Test
    void loginUser_IncorrectPassword() {

        // arrange
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        // userService.registerUser(user);
        users.save(user);
        userService.enableUser(user.getEmail());
        AppUser incorrectUser = new AppUser("a@gmail.com", "user", "wrongpassword");

        // mock
        Optional<AppUser> userOptional = Optional.of(incorrectUser);
        when(users.findByUsername(any(String.class))).thenReturn(userOptional);
        // when(encoder.matches(user.getPassword(),
        // userOptional.get().getPassword())).thenReturn(false);

        // act
        assertThrows(InvalidCredentialsException.class,
                () -> userService.loginUser(incorrectUser.getUsername(), incorrectUser.getPassword()));

        // assert
        verify(users).findByUsername(incorrectUser.getUsername());
    }

    @Test
    void getUserbyId_UserNotFound_ReturnUserIdNotFoundException() {

        // arrange
        // List<AppUser> ls = new ArrayList<>();

        // mock
        // when(users.findAll()).thenReturn(ls);

        // act
        Exception exception = assertThrows(UserIdNotFoundException.class, () -> userService.getUserById((long) 1));

        String expectedMessage = "User with ID: 1 not found";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        // assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(users).findById((long) 1);
    }

    // not completed, dk how to mock auto generated id
    @Test
    void getUserById_UserFound_ReturnUser() {
        List<AppUser> ls = new ArrayList<>();

        AppUser user = new AppUser("a@gmail.com", "user", "password");

        ls.add(user);

        when(users.findAll()).thenReturn(ls);

        AppUser returnedUser = userService.getUserById(ls.get(0).getId());

        assertEquals(returnedUser, user);
    }

    @Test
    void getUserByEmail_UserNotFound_ReturnUserNotFoundException() {
        // List<AppUser> ls = new ArrayList<>();
        // AppUser user = new AppUser("a@gmail.com", "user", "password");

        // ls.add(user);
        Optional<AppUser> optionalUser = Optional.empty();

        when(users.findByEmailIgnoreCase("a@gmail.com")).thenReturn(optionalUser);

        Exception exception = assertThrows(UserEmailNotFoundException.class,
                () -> userService.getUserByEmail("a@gmail.com"));

        String expectedMessage = "user with email a@gmail.com not found!";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        // assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(users).findByEmailIgnoreCase("a@gmail.com");
    }

    @Test
    void getUserByEmail_UserFound_ReturnUser() {
        // List<AppUser> ls = new ArrayList<>();
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        Optional<AppUser> optionalUser = Optional.of(user);

        when(users.findByEmailIgnoreCase("a@gmail.com")).thenReturn(optionalUser);

        AppUser returnedUser = userService.getUserByEmail("a@gmail.com");
        assertEquals(returnedUser, user);
        verify(users).findByEmailIgnoreCase("a@gmail.com");

    }

    @Test
    void registerUser_EmailTaken_ReturnUserEmailTakenException(){
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        Optional<AppUser> optionalUser = Optional.of(user);

        when(users.findByEmailIgnoreCase("a@gmail.com")).thenReturn(optionalUser);


        Exception exception = assertThrows(UserEmailTakenException.class,
                () -> userService.registerUser(user));

        String expectedMessage = "Email already taken";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        // assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(users).findByEmailIgnoreCase("a@gmail.com");
    }

    @Test
    void registerUser_UsernameTaken_ReturnUsernameTakenException(){
        AppUser user = new AppUser("a@gmail.com", "user", "password");
        Optional<AppUser> optionalUser = Optional.of(user);

        when(users.findByUsername("user")).thenReturn(optionalUser);

        AppUser newUser = new AppUser("ab@gmail.com", "user", "password");

        Exception exception = assertThrows(UsernameTakenException.class,
                () -> userService.registerUser(newUser));

        String expectedMessage = "Username already taken";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        // assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(users).findByUsername("user");
    }

    //how to assert a randomly generated token?
    @Test
    void registerUser_RegisterSuccess_ReturnConfirmationToken(){

    }

}
