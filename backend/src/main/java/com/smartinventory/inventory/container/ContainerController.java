package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ContainerController {
    private ContainerService containerService;

    @GetMapping("/containers")
    public List<Container> getAllContainer(){
        return containerService.listContainer();
    }

    @GetMapping("/containers/{ContainerId}")
    public Container getContainer(@PathVariable Long ContainerId, @Valid @RequestBody ContainerDTO ContainerRequest){
        return containerService.getContainer(ContainerRequest.getContainerName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/containers")
    public Container addContainer(@Valid @RequestBody Container Container) {
        return containerService.addContainer(Container);
    }

    @PutMapping("/containers/{ContainerId}")
    public Container updateContainer(@PathVariable Long ContainerId, @Valid @RequestBody Container newContainer){
        Container Container = containerService.updateContainer(ContainerId, newContainer);
        if(Container == null) throw new ContainerNotFoundException(newContainer.getContainerName());
        return Container;
    }

    @DeleteMapping("/containers/{ContainerId}")
    public void deleteContainer(@PathVariable Long ContainerId){
        try{
            containerService.deleteContainer(ContainerId);
         }catch(EmptyResultDataAccessException e) {
            throw new ContainerNotFoundException(containerService.getContainer(ContainerId).getContainerName());
         }
    }
}
