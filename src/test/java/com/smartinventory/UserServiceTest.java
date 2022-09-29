package com.smartinventory;

import com.smartinventory.user.UserRepository;
import com.smartinventory.user.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository users;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_NewUsername_SavedBook() {
        
    }
}
