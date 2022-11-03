package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartinventory.exceptions.inventory.ContainerNotFoundException;
import com.smartinventory.exceptions.inventory.StorageNotFoundException;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {

    private final StorageRepository storageRepo;
    private final ContainerRepository containerRepo;


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
}
