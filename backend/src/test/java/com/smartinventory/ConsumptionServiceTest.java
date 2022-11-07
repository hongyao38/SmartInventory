package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.inventory.consumption.Consumption;
import com.smartinventory.inventory.consumption.ConsumptionDTO;
import com.smartinventory.inventory.consumption.ConsumptionRepository;
import com.smartinventory.inventory.consumption.ConsumptionService;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

@ExtendWith(MockitoExtension.class)
public class ConsumptionServiceTest {

    @Mock
    private ConsumptionRepository consumptionRepo;

    @Mock
    private FoodRepository foodRepo;

    @Mock
    private AppUserRepository userRepo;

    @Mock
    private ContainerRepository containerRepo;

    @Mock
    private StorageRepository storageRepo;

    @InjectMocks
    private ConsumptionService consumptionService;

    @Test
    void getAllUserConsumptionsFromFood_ConsumptionsExists_returnConsumptions() {
        //Creating consumption list
        List<Consumption> consumptions = new ArrayList<>();

        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);
        
        // Creating optional user
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        Optional<AppUser> optionalUser = Optional.of(user);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(consumptionRepo.findByFoodAndUser(any(Food.class), any(AppUser.class))).thenReturn(consumptions);

        List<Consumption> returnedList = consumptionService.getAllUserConsumptionsFromFood("TestUser", "Milk");
        assertEquals(consumptions, returnedList);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(consumptionRepo).findByFoodAndUser(food, user);

    }

    @Test
    void getAllUserConsumptionsFromFood_NoConsumptions_returnEmptyList() {
        //Creating consumption list
        List<Consumption> consumptions = new ArrayList<>();

        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);
        
        // Creating optional user
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        Optional<AppUser> optionalUser = Optional.of(user);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(consumptionRepo.findByFoodAndUser(any(Food.class), any(AppUser.class))).thenReturn(null);

        List<Consumption> returnedList = consumptionService.getAllUserConsumptionsFromFood("TestUser", "Milk");
        assertEquals(consumptions, returnedList);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(consumptionRepo).findByFoodAndUser(food, user);

    }

    @Test
    void getConsumption_ConsumptionFound_ReturnConsumption() {
        // Create a zoned date time
        ZonedDateTime dateTime = ZonedDateTime.of(2022, 10, 10, 10, 10, 10, 10, ZoneId.of("Asia/Dubai"));

        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);
        
        // Creating optional user
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        user.setAuthorities("ROLE_USER");
        Optional<AppUser> optionalUser = Optional.of(user);

        //Creating consumption
        Consumption consumption = new Consumption();
        Optional<Consumption> optionalConsumption = Optional.of(consumption);
        // consumption.setDateTime(dateTime);
        // consumption.setUser(user);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(consumptionRepo.findByFoodAndUserAndDateTime(any(Food.class), any(AppUser.class), any(ZonedDateTime.class))).thenReturn(optionalConsumption);

        Consumption returnedConsumption = consumptionService.getConsumption("TestUser", "Milk", "10.10.2022 10:10:10:10 Asia/Dubai");
        assertEquals(consumption, returnedConsumption);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(consumptionRepo).findByFoodAndUserAndDateTime(food, user, dateTime);
    }

    @Test
    void addConsumption_NewConsumption_ReturnConsumption() {
        // Creating consumption
        Consumption consumption = new Consumption();

        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);
        
        // Creating optional user
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        Optional<AppUser> optionalUser = Optional.of(user);

        //Create optional storage
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        //Create optional container
        Container container = new Container();
        container.setStorage(storage);
        container.setFood(food);
        container.setQuantity(5.0);
        Optional<Container> optionalContainer = Optional.of(container);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(containerRepo.findByStorageAndFood(any(Storage.class), any(Food.class))).thenReturn(optionalContainer);
        when(containerRepo.save(any(Container.class))).thenReturn(container);
        when(consumptionRepo.save(any(Consumption.class))).thenReturn(consumption);

        ConsumptionDTO consumptionRequest = new ConsumptionDTO(2.0, ZonedDateTime.of(2022, 11, 2, 10, 10, 10, 10, ZoneId.of("Asia/Dubai")));
        Consumption testConsumption = consumptionService.addConsumption("TestUser", "Milk", consumptionRequest);
        assertEquals(consumption, testConsumption);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(storageRepo).findByUsername("TestUser");
        verify(containerRepo).findByStorageAndFood(storage, food);
        verify(containerRepo).save(container);
        verify(consumptionRepo).save(any(Consumption.class));

    }

    @Test
    void updateConsumption_ConsumptionFound_ReturnUpdatedConsumption() {


        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);

        // Creating consumption
        Consumption consumption = new Consumption();
        consumption.setQuantity(5.0);
        consumption.setFood(food);
        Optional<Consumption> optionalConsumption = Optional.of(consumption);

        // Creating optional user
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        Optional<AppUser> optionalUser = Optional.of(user);

        //Create optional storage
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        //Create optional container
        Container container = new Container();
        container.setStorage(storage);
        container.setFood(food);
        container.setQuantity(5.0);
        Optional<Container> optionalContainer = Optional.of(container);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(containerRepo.findByStorageAndFood(any(Storage.class), any(Food.class))).thenReturn(optionalContainer);
        when(containerRepo.save(any(Container.class))).thenReturn(container);
        when(consumptionRepo.findByFoodAndUserAndDateTime(any(Food.class), any(AppUser.class), any(ZonedDateTime.class))).thenReturn(optionalConsumption);
        when(consumptionRepo.save(any(Consumption.class))).thenReturn(consumption);

        ZonedDateTime dateTime = ZonedDateTime.of(2022, 10, 10, 10, 10, 10, 10, ZoneId.of("Asia/Dubai"));
        ConsumptionDTO consumptionRequest = new ConsumptionDTO(2.0, dateTime);
        Consumption testConsumption = consumptionService.updateConsumption("TestUser", "Milk", "10.10.2022 10:10:10:10 Asia/Dubai",consumptionRequest);
        assertEquals(consumption, testConsumption);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(storageRepo).findByUsername("TestUser");
        verify(containerRepo).findByStorageAndFood(storage, food);
        verify(containerRepo).save(container);
        verify(consumptionRepo).findByFoodAndUserAndDateTime(food, user, dateTime);
        verify(consumptionRepo).save(any(Consumption.class));

    }
}

