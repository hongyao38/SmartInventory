package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;
import com.smartinventory.inventory.storage.StorageService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {
    @Mock
    private StorageRepository storageRepo;

    @InjectMocks
    private StorageService storageService;

    @Test
    void addStorage_returnStorage() {
        AppUser user = new AppUser();
        user.setUsername("user");
        Storage storage = new Storage("user");
        System.out.println("created storage = " + storage);

        //mock save method
        when(storageRepo.save(any(Storage.class))).thenReturn(storage);

        Storage addedStorage = storageService.addStorage(user);
        System.out.println("added storage =  " + addedStorage);
        assertEquals(storage, addedStorage);

        // System.out.println(storage);
        // System.out.println(storageRepo.save(storage));
        verify(storageRepo).save(storage);
    }
}
