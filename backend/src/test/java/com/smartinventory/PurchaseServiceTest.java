// package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.purchase.Purchase;
import com.smartinventory.inventory.purchase.PurchaseDTO;
import com.smartinventory.inventory.purchase.PurchaseRepository;
import com.smartinventory.inventory.purchase.PurchaseService;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class)
// public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepo;

    @Mock
    private FoodRepository foodRepo;

    @Mock
    private AppUserRepository userRepo;

    @Mock
    private ContainerRepository containerRepo;

    @Mock
    private StorageRepository storageRepo;

    @InjectMocks
    private PurchaseService PurchaseService;

    @Test
    void getAllUserPurchasesFromFood_PurchasesExists_returnPurchases() {
        //Creating Purchase list
        List<Purchase> Purchases = new ArrayList<>();

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
        when(purchaseRepo.findByFoodAndUser(any(Food.class), any(AppUser.class))).thenReturn(Purchases);

        List<Purchase> returnedList = PurchaseService.getAllUserPurchasesFromFood("TestUser", "Milk");
        assertEquals(Purchases, returnedList);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(purchaseRepo).findByFoodAndUser(food, user);

    }

    @Test
    void getAllUserPurchasesFromFood_NoPurchases_returnEmptyList() {
        //Creating Purchase list
        List<Purchase> Purchases = new ArrayList<>();

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
        when(purchaseRepo.findByFoodAndUser(any(Food.class), any(AppUser.class))).thenReturn(null);

        List<Purchase> returnedList = PurchaseService.getAllUserPurchasesFromFood("TestUser", "Milk");
        assertEquals(Purchases, returnedList);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(purchaseRepo).findByFoodAndUser(food, user);

    }

    @Test
    void getPurchase_PurchaseFound_ReturnPurchase() {
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

        //Creating Purchase
        Purchase Purchase = new Purchase();
        Optional<Purchase> optionalPurchase = Optional.of(Purchase);
        // Purchase.setDateTime(dateTime);
        // Purchase.setUser(user);

        // mock methods
        when(foodRepo.findByName("Milk")).thenReturn(optionalFood);
        when(userRepo.findByUsername("TestUser")).thenReturn(optionalUser);
        when(purchaseRepo.findByFoodAndUserAndDateTime(any(Food.class), any(AppUser.class), any(ZonedDateTime.class))).thenReturn(optionalPurchase);

        Purchase returnedPurchase = PurchaseService.getPurchase("TestUser", "Milk", "10.10.2022 10:10:10:10 Asia/Dubai");
        assertEquals(Purchase, returnedPurchase);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(purchaseRepo).findByFoodAndUserAndDateTime(food, user, dateTime);
    }

    @Test
    void addPurchase_NewPurchase_ReturnPurchase() {
        // Creating Purchase
        Purchase Purchase = new Purchase();

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
        when(purchaseRepo.save(any(Purchase.class))).thenReturn(Purchase);

        PurchaseDTO PurchaseRequest = new PurchaseDTO(2.0, ZonedDateTime.of(2022, 11, 2, 10, 10, 10, 10, ZoneId.of("Asia/Dubai")),
                                                                ZonedDateTime.of(2022, 11, 2, 10, 10, 10, 10, ZoneId.of("Asia/Dubai")));
        Purchase testPurchase = PurchaseService.addPurchase("TestUser", "Milk", PurchaseRequest);
        assertEquals(Purchase, testPurchase);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(storageRepo).findByUsername("TestUser");
        verify(containerRepo).findByStorageAndFood(storage, food);
        verify(containerRepo).save(container);
        verify(purchaseRepo).save(any(Purchase.class));

    }

    @Test
    void updatePurchase_PurchaseFound_ReturnUpdatedPurchase() {


        //Create optional food
        Food food = new Food("Milk");
        Optional<Food> optionalFood = Optional.of(food);

        // Creating Purchase
        Purchase Purchase = new Purchase();
        Purchase.setQuantityBought(5.0);
        Purchase.setFood(food);
        Optional<Purchase> optionalPurchase = Optional.of(Purchase);

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
        when(purchaseRepo.findByFoodAndUserAndDateTime(any(Food.class), any(AppUser.class), any(ZonedDateTime.class))).thenReturn(optionalPurchase);
        when(purchaseRepo.save(any(Purchase.class))).thenReturn(Purchase);

        ZonedDateTime dateTime = ZonedDateTime.of(2022, 10, 10, 10, 10, 10, 10, ZoneId.of("Asia/Dubai"));
        PurchaseDTO PurchaseRequest = new PurchaseDTO(2.0, dateTime, dateTime);
        Purchase testPurchase = PurchaseService.updatePurchase("TestUser", "Milk", "10.10.2022 10:10:10:10 Asia/Dubai",PurchaseRequest);
        assertEquals(Purchase, testPurchase);

        verify(foodRepo).findByName("Milk");
        verify(userRepo).findByUsername("TestUser");
        verify(storageRepo).findByUsername("TestUser");
        verify(containerRepo).findByStorageAndFood(storage, food);
        verify(containerRepo).save(container);
        verify(purchaseRepo).findByFoodAndUserAndDateTime(food, user, dateTime);
        verify(purchaseRepo).save(any(Purchase.class));

    }
}

