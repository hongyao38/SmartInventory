package com.smartinventory.inventory.storage;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StorageController {
    private StorageService storageService;

    @GetMapping("/storage")
    public List<Storage> getAllStorage(){
        return storageService.listStorage();
    }

    @GetMapping("/storage/{storgeId}")
    public Storage getContainer(@PathVariable (value = "storageId") Long storageId){
        return storageService.getStorage(storageId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/storage")
    public Storage addStorage(@Valid @RequestBody Storage storage) {
        return storageService.addStorage(storage);
    }

    @PutMapping("/storage/{storageId}")
    public Storage updateStorage(@PathVariable (value = "storageId") Long storageId, @Valid @RequestBody Storage newStorage){
        return storageService.updateStorage(storageId, newStorage);
    }

    @DeleteMapping("/storage/{storageId}")
    public void deleteStorage(@PathVariable (value = "storageId") Long storageId){
        try{
            storageService.deleteStorage(storageId);
         }catch(EmptyResultDataAccessException e) {

         }
    }
}
