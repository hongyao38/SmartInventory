package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.inventory.food.Food;

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

    //find container by row and col idx
    public Container getContainer(int rowIdx, int colIdx) {
        Optional<Container> container = containerRepo.findByRowIndexAndColIndex(rowIdx, colIdx);

        if (container.isEmpty()) {
            throw new ContainerNotFoundException();
        }

        return container.get();
    }

    //add new Container
    public Container addContainer(Container container) {
        Double currentQuantity = container.getFood().getCurrentQuantity();
        Double capacity = container.getCapacity();
        Double percentageFilled = currentQuantity / capacity * 100;

        container.setPercentageFilled(percentageFilled);
        return containerRepo.save(container);
    }

    public Container updateContainer(Container container, Double quantity) {
        Double newPercentage = quantity / container.getCapacity() * 100;

        container.setPercentageFilled(newPercentage);
        return containerRepo.save(container);
    }

    public Container updateContainer(Long containerId, Container newContainer) {
        Container currentContainer = containerRepo.findById(containerId).get();
        Double currentQuantity = currentContainer.getFood().getCurrentQuantity();
        Double percentageFilled = currentQuantity / newContainer.getCapacity() * 100;
        currentContainer.setPercentageFilled(percentageFilled);

        currentContainer.setThreshold(newContainer.getThreshold());
        currentContainer.setCapacity(newContainer.getCapacity());
        return containerRepo.save(currentContainer);
    }

    public void deleteContainer(Long ContainerId) {
        containerRepo.deleteById(ContainerId);
    }
}