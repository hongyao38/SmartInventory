package com.smartinventory.inventory.container;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartinventory.inventory.food.FoodDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1")
public class ContainerController {

    private final ContainerService containerService;

    // Add new container
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/containers/{username}")
    public Container addContainer(@PathVariable("username") String username,
            @Valid @RequestBody ContainerDTO containerRequest) {
        return containerService.addContainer(username, containerRequest);
    }

    // Get a list of all Containeres
    @GetMapping("/containers/{username}")
    public List<Container> getAllContainersFromUser(@PathVariable("username") String username) {
        return containerService.getAllContainersFromUser(username);
    }

    // Get an individual container
    @GetMapping("/containers/{username}/{i}_{j}")
    public Container getContainer(@PathVariable("username") String username,
                                    @PathVariable("i") Integer i,
                                    @PathVariable("j") Integer j) {
        return containerService.getContainer(username, i, j);
    }

    // Add Food to Container
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/containers/{username}/{i}_{j}")
    public int addFoodToContainer(@PathVariable("username") String username,
                                        @PathVariable("i") Integer i,
                                        @PathVariable("j") Integer j,
                                        @RequestBody FoodDTO foodRequest) {
        return containerService.addFoodToContainer(username, i, j, foodRequest);
    }

    // Update Container Capacity
    @PutMapping("/containers/{username}")
    public int updateContainerCapacity(@PathVariable("username") String username,
                                        @RequestBody ContainerDTO containerRequest) {
        return containerService.updateContainerCapacity(username, containerRequest);
    }
}
