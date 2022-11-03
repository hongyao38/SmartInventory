package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.exceptions.inventory.StorageNotFoundException;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {
    
    private final ContainerRepository containerRepo;
    private final StorageRepository storageRepo;

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

    //find container by row and col idx
    public Container getContainer(int rowIdx, int colIdx) {
        Optional<Container> container = containerRepo.findByRowAndCol(rowIdx, colIdx);

        if (container.isEmpty()) {
            throw new ContainerNotFoundException();
        }

        return container.get();
    }

    //add new Container
    public Container addContainer(Long storageId, ContainerDTO containerRequest) {
        Optional<Storage> storage = storageRepo.findById(storageId);

        if (storage.isEmpty()) {
            throw new StorageNotFoundException(storageId);
        }

        Container container = new Container(containerRequest.getCapacity(), 
                                            containerRequest.getRow(), 
                                            containerRequest.getCol(),
                                            storage.get());

        return containerRepo.save(container);
    }

    // public Container addFoodToContainer(Food food) {
    //             Double capacity = newContainer.getCapacity();
    //             Double percentageFilled = currentQuantity / capacity * 100;
        
    //             newContainer.setPercentageFilled(percentageFilled);
    // }

    // public Container updateContainer(Container container, Double quantity) {
    //     Double newPercentage = quantity / container.getCapacity() * 100;

    //     container.setPercentageFilled(newPercentage);
    //     return containerRepo.save(container);
    // }

    // public Container updateContainer(Long containerId, Container newContainer) {
    //     Container currentContainer = containerRepo.findById(containerId).get();
    //     Double currentQuantity = currentContainer.getFood().getCurrentQuantity();
    //     Double percentageFilled = currentQuantity / newContainer.getCapacity() * 100;
    //     currentContainer.setPercentageFilled(percentageFilled);

    //     currentContainer.setThreshold(newContainer.getThreshold());
    //     currentContainer.setCapacity(newContainer.getCapacity());
    //     return containerRepo.save(currentContainer);
    // }

    public void deleteContainer(Long ContainerId) {
        containerRepo.deleteById(ContainerId);
    }
}