package com.smartinventory.inventory.storage;

import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.inventory.food.Food;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {
    
    private final StorageRepository storageRepo;

    //list all containers
    public List<Storage> listStorage() {
        return storageRepo.findAll();
    }

    //find container by capacity
    // public List<Storage> listStorageBySize(int size) {
    //     return storageRepo.findBySize(size);
    // }

    //find container by id
    public Storage getStorage(Long storageId) {
        return storageRepo.findById(storageId).get();
    }

    public void deleteStorage(Long storageId) {
        storageRepo.deleteById(storageId);
    }
}
