package com.smartinventory.inventory.storage;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {
    
    private final StorageRepository storageRepo;
    private final AppUserRepository userRepo;

    //find container by capacity
    // public List<Storage> listStorageBySize(int size) {
    //     return storageRepo.findBySize(size);
    // }

    // //create new storage
    // public Storage createStorage(String username) {
    //     return new Storage(username);
    // }

    //find container by id
    public Storage getStorage(String username) {
        Optional<AppUser> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return storageRepo.findByUsername(username).get();
    }

    public Storage addStorage(AppUser user) {
        // Storage storage = createStorage(user.getUsername());
        Storage storage = new Storage(user.getUsername());

        return storageRepo.save(storage);
    }
}
