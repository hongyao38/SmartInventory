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

    //list all storage
    public List<Storage> listStorage() {
        return storageRepo.findAll();
    }

    //find storage by size
    public List<Storage> listStorageBySize(Double size) {
        return storageRepo.findBySize(size);
    }

    //find storage by id
    public Storage getStorage(Long storageId) {
        return storageRepo.findById(storageId).get();
    }

    //add new storage
    public Storage addStorage(Storage storage) {
        int size = storage.getSize();
        storage.setStorageLeft(size);
        return storageRepo.save(storage);
    }

    public Storage updateStorage(Long storageId, Storage newStorage) {
        Storage updatedStorage = storageRepo.findById(storageId).get();
        updatedStorage.setSize(newStorage.getSize());
        return storageRepo.save(updatedStorage);
    }

    public void deleteStorage(Long storageId) {
        storageRepo.deleteById(storageId);
    }
}