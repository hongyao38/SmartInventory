package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.smartinventory.security.token.ConfirmationTokenService;
import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.appuser.AppUserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    void addUser_NewUsername_SavedBook() {

        AppUser user = new AppUser("a@gmail.com", "user", "password");

        Optional<AppUser> userOptional = Optional.empty();
        when(users.findByUsername(any(String.class))).thenReturn(userOptional);
        when(users.findByEmailIgnoreCase(any(String.class))).thenReturn(userOptional);

        String regUser = userService.registerUser(user);
        assertNotNull(regUser);

        verify(users).findByUsername(user.getUsername());
        verify(users).findByEmailIgnoreCase(user.getEmail());
    }
}
