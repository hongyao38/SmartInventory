package com.smartinventory.inventory.container;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartinventory.inventory.blockCoordinates.ContainerCoordinatesRepository;
import com.smartinventory.inventory.containerCoordinates.ContainerCoordinates;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContainerService {

    private final StorageRepository storageRepo;
    private final ContainerRepository containerRepo;
    private final ContainerCoordinatesRepository coordsRepo;


    /**
     * Add new container for user
     * @param username
     * @param containerRequest
     * @return
     */
    public Container addContainer(String username, ContainerDTO containerRequest) {
        // TODO: Might need to check optional isEmpty()
        Storage storage = storageRepo.findByUsername(username).get();

        Container newContainer = new Container(containerRequest.getCapacity(),
                containerRequest.getI(),
                containerRequest.getJ(),
                storage);

        ContainerCoordinates coords = new ContainerCoordinates(newContainer, storage);
        coordsRepo.save(coords);

        return containerRepo.save(newContainer);
    }


    /**
     * Takes in username and return a list of container coordinates used for rendering
     * storage space upon logging in
     * @param username
     * @return List<ContainerCoordinates> containerCoords
     */
    public List<ContainerCoordinates> getAllContainersFromUser(String username) {

        // Find storage to get container coordinates
        // TODO: Might need to check optional isEmpty()
        Storage storage = storageRepo.findByUsername(username).get();

        // Get a list of all container coordinates belonging to user
        List<ContainerCoordinates> containerCoords = coordsRepo.findByStorage(storage).get();
        System.out.println(containerCoords);
        return containerCoords;
    }
}
