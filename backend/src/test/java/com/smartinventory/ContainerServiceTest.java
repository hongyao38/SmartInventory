package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventory.exceptions.inventory.storage.StorageNotFoundException;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.container.ContainerDTO;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.container.ContainerService;
import com.smartinventory.inventory.food.FoodDTO;
import com.smartinventory.inventory.food.FoodService;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

@ExtendWith(MockitoExtension.class)
public class ContainerServiceTest {
    
    @Mock
    private StorageRepository storageRepo;

    @Mock
    private ContainerRepository containerRepo;

    @Mock
    private FoodService foodService;

    @InjectMocks
    private ContainerService containerService;

    @Test
    void addContainer_storagePresent_returnContainer() {
        //Create objects
        Container container = new Container();
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);
        ContainerDTO containerRequest = new ContainerDTO(5.0, 1, 1);
        
        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(containerRepo.save(any(Container.class))).thenReturn(container);

        //Assert
        Container addedContainer = containerService.addContainer("TestUser", containerRequest);
        assertEquals(container, addedContainer);

        //verify
        verify(storageRepo).findByUsername("TestUser");
        verify(containerRepo).save(any(Container.class));

    }

    @Test
    void addContainer_storageNotFound_throwException() {
        //Create objects
        Optional<Storage> optionalStorage = Optional.empty();
        ContainerDTO containerRequest = new ContainerDTO(5.0, 1, 1);
        
        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        //Assert
        assertThrows(StorageNotFoundException.class, () -> containerService.addContainer("TestUser", containerRequest));

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getAllContainersFromUser_storagePresent_returnList() {
        //Create objects
        List<Container> containers = new ArrayList<>();
        Optional<List<Container>> optionalContainer = Optional.of(containers);

        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(containerRepo.findByStorage(any(Storage.class))).thenReturn(optionalContainer);

        //Assert
        List<Container> containerList = containerService.getAllContainersFromUser("TestUser");
        assertEquals(containers, containerList);

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getAllContainersFromUser_storageNotFound_throwException() {
        //Create objects
        Optional<Storage> optionalStorage = Optional.empty();
        
        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        //Assert
        assertThrows(StorageNotFoundException.class, () -> containerService.getAllContainersFromUser("TestUser"));

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getContainer_StorageFound_ReturnContainer() {
        //Create objects
        Container container = new Container();
        container.setI(1);
        container.setJ(1);
        Optional<Container> optionalContainer = Optional.of(container);

        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(containerRepo.findByIAndJAndStorage(any(Integer.class), any(Integer.class), any(Storage.class))).thenReturn(optionalContainer);

        //Assert
        Container containerFound = containerService.getContainer("TestUser", container.getI(), container.getJ());
        assertEquals(container, containerFound);

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getContainer_storageNotFound_throwException() {
        //Create objects
        Optional<Storage> optionalStorage = Optional.empty();
        
        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        //Assert
        assertThrows(StorageNotFoundException.class, () -> containerService.getContainer("TestUser", 1, 1));

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }

    // @Test
    // void addFoodToContainer_StorageFound_ReturnContainer() {
    //     //Create objects
    //     Storage storage = new Storage();
    //     storage.setUsername("TestUser");
    //     Optional<Storage> optionalStorage = Optional.of(storage);

    //     FoodDTO foodRequest = new FoodDTO("Milk", 2.0);
    //     Food food = new Food("Milk");

    //     Container container = new Container();
    //     container.setId((long) 1);
    //     container.setI(1);
    //     container.setJ(1);
    //     container.setQuantity(0.0);
    //     container.setCapacity(10.0);
    //     // container.setFood(food);
    //     Optional<Container> optionalContainer = Optional.of(container);

    //     // Mock methods
    //     when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
    //     when(containerRepo.findByIAndJAndStorage(any(Integer.class), any(Integer.class), any(Storage.class))).thenReturn(optionalContainer);
    //     when(containerRepo.updateContainerWithFood(any(Food.class), any(Double.class), anyLong())).thenReturn(1);

    //     //Assert
    //     int returnQuantity = containerService.addFoodToContainer("TestUser", container.getI(), container.getJ(), foodRequest);
    //     assertEquals(1, returnQuantity);

    //     //verify
    //     verify(storageRepo).findByUsername("TestUser");
    //     verify(containerRepo).findByIAndJAndStorage(container.getI(), container.getJ(), storage);
    //     verify(containerRepo).updateContainerWithFood(food, 2.0, container.getId());
    // }

    @Test
    void addFoodToContainer_storageNotFound_throwException() {
        //Create objects
        Optional<Storage> optionalStorage = Optional.empty();
        FoodDTO foodRequest = new FoodDTO();
        
        // Mock methods
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        //Assert
        assertThrows(StorageNotFoundException.class, () -> containerService.addFoodToContainer("TestUser", 1, 1, foodRequest));

        //verify
        verify(storageRepo).findByUsername("TestUser");
    }
}
