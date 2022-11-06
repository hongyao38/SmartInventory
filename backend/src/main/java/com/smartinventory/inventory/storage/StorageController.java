package com.smartinventory.inventory.storage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StorageController {
    private StorageService storageService;

    @GetMapping("/{username}")
    public Storage getStorage(@PathVariable (value = "username") String username){
        return storageService.getStorage(username);
    }

}
