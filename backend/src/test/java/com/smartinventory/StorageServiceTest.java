package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;
import com.smartinventory.inventory.storage.StorageService;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {
    @Mock
    private StorageRepository storageRepo;

    @Mock
    private AppUserRepository userRepo;

    @InjectMocks
    private StorageService storageService;

    @Test
    void addStorage_returnStorage() {
        AppUser user = new AppUser();
        user.setUsername("user");
        Storage storage = new Storage(user.getUsername());
        
        // System.out.println("created storage = " + storage);

        //mock save method
        when(storageRepo.save(any(Storage.class))).thenReturn(storage);

        Storage addedStorage = storageService.addStorage(user);
        // System.out.println("created storage =  " + new Storage(user.getUsername()));
        // System.out.println("added storage =  " + addedStorage);
        assertEquals(storage, addedStorage);

        // System.out.println(storage);
        // System.out.println(storageRepo.save(storage));
        verify(storageRepo).save(any(Storage.class));
    }

    @Test
    void getStorage_userExists_returnStorage() {
        AppUser user = new AppUser();
        user.setUsername("testUsername");
        Storage storage = new Storage();
        storage.setUsername(user.getUsername());


        Optional<AppUser> optionalUser = Optional.of(user);
        Optional<Storage> optionalStorage = Optional.of(storage);
        when(userRepo.findByUsername(user.getUsername())).thenReturn(optionalUser);
        when(storageRepo.findByUsername(any(String.class))).thenReturn(optionalStorage);

        Storage getStorage = storageService.getStorage(user.getUsername());
        assertEquals(storage, getStorage);

        verify(userRepo).findByUsername(user.getUsername());
        verify(storageRepo).findByUsername(user.getUsername());
    }

    @Test
    void getStorage_userNotFound_ReturnException() {

        when(userRepo.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> storageService.getStorage("testUsername"));

        verify(userRepo).findByUsername("testUsername");
    }
}
