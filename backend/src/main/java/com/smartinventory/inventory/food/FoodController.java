package com.smartinventory.inventory.food;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FoodController {

        // // Add new container
        // @ResponseStatus(HttpStatus.CREATED)
        // @PostMapping("/containers/{username}")
        // public Container addContainer(@PathVariable("username") String username,
        //         @Valid @RequestBody ContainerDTO containerRequest) {
        //     return containerService.addContainer(username, containerRequest);
        // }

        // //Get a list of all food for the user
        // @GetMapping("/food/{username}")

    
        // // Get a list of all Containeres
        // @GetMapping("/containers/{username}")
        // public List<Container> getAllContainersFromUser(@PathVariable("username") String username) {
        //     return containerService.getAllContainersFromUser(username);
        // }
    
        // // Get an individual container
        // @GetMapping("/containers/{username}/{i}_{j}")
        // public Container getContainer(@PathVariable("username") String username,
        //                                 @PathVariable("i") Integer i,
        //                                 @PathVariable("j") Integer j) {
        //     return containerService.getContainer(username, i, j);
        // }
}
