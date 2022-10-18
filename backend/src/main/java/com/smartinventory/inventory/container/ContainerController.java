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

    @GetMapping("/storage/{storageId}/containers")
    public List<Container> getAllContainer(){
        return containerService.listContainer();
    }

    @GetMapping("/containers/{containerId}")
    public Container getContainer(@PathVariable Long containerId){
        return containerService.getContainer(containerId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/food/{foodId}/containers")
    public Container addContainer(@PathVariable (value = "foodId") Long foodId,
                                    @Valid @RequestBody Container container) {
        Food food = foodService.getFood(foodId);
        container.setFood(food);

        return containerService.addContainer(container);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/containers/{containerId}/storage/{storageId}")
    public Container addContainertoStorage(@PathVariable (value = "containerId") Long containerId,
                                    @PathVariable (value = "storageId") Long storageId) {

        Storage storage = storageService.getStorage(storageId);
        Container container = containerService.getContainer(containerId);
        container.setStorage(storage);

        return containerService.updateContainer(containerId, container);
    }

    @PutMapping("/containers/{containerId}/food/{foodId}")
    public Container updateContainer(@PathVariable (value = "containerId") Long containerId, @Valid @RequestBody Container newContainer){
        Container container = containerService.updateContainer(containerId, newContainer);
        return container;
    }

    // //adding container to storage space
    // @PutMapping("food/{foodId}/containers/{containerId}/storage/{storageId}")
    // public Container updateContainer(@PathVariable Long containerId,
    //                                     @PathVariable Long storageId){
    //     Storage storage = storageService.getStorage(storageId);
    //     Container container = containerService.getContainer(containerId);
    //     container.setStorage(storage);
    //     return containerService.updateContainer(containerId, container);
    // }

    @DeleteMapping("/storage/{storageId}/containers/{containerId}")
    public void deleteContainer(@PathVariable Long containerId){
        try{
            containerService.deleteContainer(containerId);
         }catch(EmptyResultDataAccessException e) {

         }
    }
}
