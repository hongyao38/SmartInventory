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
    public List<Storage> listStorageBySize(int size) {
        return storageRepo.findBySize(size);
    }

    //find container by id
    public Storage getStorage(Long storageId) {
        return storageRepo.findById(storageId).get();
    }

    //add new Container
    public Storage addStorage(Storage storage) {
        storage.setStorageLeft(storage.getSize());

        return storageRepo.save(storage);
    }

    public Storage updateStorage(Long storageId, Storage newStorage) {
        Storage updatedStorage = storageRepo.findById(storageId).get();
        updatedStorage.setSize(newStorage.getSize());
        updatedStorage.setStorageLeft(newStorage.getSize());

        return storageRepo.save(updatedStorage);
    }

    public Storage updateStorageAddContainer(Long storageId) {
        Storage updatedStorage = storageRepo.findById(storageId).get();
        int newStorageLeft = updatedStorage.getStorageLeft() - 1;

        updatedStorage.setStorageLeft(newStorageLeft);

        return storageRepo.save(updatedStorage);
    }

    public void deleteStorage(Long storageId) {
        storageRepo.deleteById(storageId);
    }
}
