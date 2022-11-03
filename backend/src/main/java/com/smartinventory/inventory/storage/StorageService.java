package com.smartinventory.inventory.storage;

import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    //find container by id
    public Storage getStorage(String username) {
        Optional<AppUser> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return storageRepo.findByUsername(username).get();
    }

    public Storage addStorage(AppUser user) {
        Storage storage = new Storage(user.getUsername());

        return storageRepo.save(storage);
    }
}
