package com.smartinventory.inventory.container;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ContainerController {

    private final ContainerService containerService;

    @GetMapping("/containers/{i}_{j}")
    public Container getContainer(@PathVariable("i") Integer i, @PathVariable("j") Integer j){
        return containerService.getContainer(i, j);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/containers")
    public Container addContainer(@Valid @RequestBody ContainerDTO containerRequest) {

        return containerService.addContainer(containerRequest);
    }

    
}
