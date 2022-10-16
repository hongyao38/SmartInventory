package com.smartinventory.inventory.container;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {
    
    private final ContainerRepository containerRepo;

    //list all containers
    public List<Container> listContainer() {
        return containerRepo.findAll();
    }

    //find container by capacity
    public List<Container> listContainerbyCapacity(Double capacity) {
        return containerRepo.findByCapacity(capacity);
    }

    //find container by id
    public Container getContainer(Long containerId) {
        return containerRepo.findById(containerId).get();
    }

    //add new Container
    public Container addContainer(Container Container) {
        return containerRepo.save(Container);
    }

    public Container updateContainer(Long ContainerId, Container newContainer) {
        return containerRepo.findById(ContainerId).map(Container -> {Container.setPercentageFilled(newContainer.getPercentageFilled());
            return containerRepo.save(Container);
        }).orElse(null);
    }

    public void deleteContainer(Long ContainerId) {
        containerRepo.deleteById(ContainerId);
    }
}

//update the value of percentagefilled everytime the food is updated