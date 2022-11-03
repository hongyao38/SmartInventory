package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodService;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageService;

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
public class ContainerController {
    private ContainerService containerService;
    private FoodService foodService;
    private StorageService storageService;

    @GetMapping("/containers")
    public List<Container> getAllContainer(){
        return containerService.listContainer();
    }

    @GetMapping("/food/{foodId}/containers/{containerId}")
    public Container getContainer(@PathVariable Long containerId){
        return containerService.getContainer(containerId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("storage/{storageId}/containers")
    public Container addContainer(@PathVariable (value = "storageId") Long storageId,
                                    @Valid @RequestBody Container container) {
        // Storage storage = storageService.getStorage(storageId);

        // container.setStorage(storage);
        // storageService.updateStorageAddContainer(storageId);

        return containerService.addContainer(container);
    }

    @PutMapping("food/{foodId}/containers/{containerId}")
    public Container updateContainer(@PathVariable Long containerId, @Valid @RequestBody Container newContainer){
        Container Container = containerService.updateContainer(containerId, newContainer);
        return Container;
    }

    @DeleteMapping("food/{foodId}/containers/{containerId}")
    public void deleteContainer(@PathVariable Long containerId){
        try{
            containerService.deleteContainer(containerId);
         }catch(EmptyResultDataAccessException e) {

         }
    }
}
