package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.exceptions.inventory.ContainerTooSmallException;
import com.smartinventory.exceptions.inventory.StorageNotFoundException;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodDTO;
import com.smartinventory.inventory.food.FoodService;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {

    private final StorageRepository storageRepo;
    private final ContainerRepository containerRepo;
    private final FoodService foodService;


    /**
     * Find storage belonging to username
     * @param username
     * @return storage
     */
    private Storage getStorageByUsername(String username) {
        Optional<Storage> storage = storageRepo.findByUsername(username);
        if (storage.isEmpty()) {
            throw new StorageNotFoundException(username);
        }
        return storage.get();
    }


    /**
     * Add new container for user
     * @param username
     * @param containerRequest
     * @return
     */
    public Container addContainer(String username, ContainerDTO containerRequest) {
        // TODO: Might need to check optional isEmpty()
        Storage storage = getStorageByUsername(username);

        // Creating new container
        Container newContainer = new Container(containerRequest.getCapacity(),
                                                containerRequest.getI(),
                                                containerRequest.getJ(),
                                                storage);

        return containerRepo.save(newContainer);
    }


    /**
     * Takes in username and return a list of container coordinates used for rendering
     * storage space upon logging in
     * @param username
     * @return List<ContainerCoordinates> containerCoords
     */
    public List<Container> getAllContainersFromUser(String username) {

        // Find storage to get container coordinates
        // TODO: Might need to check optional isEmpty()
        Storage storage = getStorageByUsername(username);

        // Get a list of all container coordinates belonging to user
        List<Container> containers = containerRepo.findByStorage(storage).get();
        System.out.println(containers);
        return containers;
    }

    /**
     * Retrieve a specific container belonging to username
     * @param username
     * @param i
     * @param j
     * @return Container
     */
    public Container getContainer(String username, Integer i, Integer j) {
        Storage storage = getStorageByUsername(username);

        Optional<Container> container = containerRepo.findByIAndJAndStorage(i, j, storage);
        if (container.isEmpty()) {
            throw new ContainerNotFoundException();
        }
        return container.get();
    }


    /**
     * Creates a new listing of food if it has yet to exist and adds it to a user's container
     * @param username
     * @param i
     * @param j
     * @param foodRequest
     * @return int
     */
    public int addFoodToContainer(String username, Integer i, Integer j, FoodDTO foodRequest) {

        // Get Container
        Container container = getContainer(username, i, j);

        // If quantity is larger than container capacity
        Double newQuantity = foodRequest.getQuantity() + container.getQuantity();
        if (newQuantity > container.getCapacity()) {
            throw new ContainerTooSmallException();
        }

        // Get/Add food to list of foods
        Food food = foodService.addNewFood(foodRequest);

        // Update food to container
        return containerRepo.updateContainerWithFood(food, newQuantity, container.getId());
    }
}
