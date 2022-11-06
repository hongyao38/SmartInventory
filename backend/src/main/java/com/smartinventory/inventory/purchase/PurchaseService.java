package com.smartinventory.inventory.purchase;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.inventory.consumption.Consumption;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PurchaseService {
    
    private final PurchaseRepository purchaseRepo;
    private final FoodRepository foodRepo;
    private final AppUserRepository userRepo;
    private final ContainerRepository containerRepo;
    private final StorageRepository storageRepo;

    public List<Purchase> getAllUserPurchasesFromFood(String username, String foodName) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        List<Purchase> purchases = purchaseRepo.findByFoodAndUser(food.get(), user.get());

        if (purchases == null) {
            purchases = new ArrayList<Purchase>();
        }

        return purchases;
    }

    public Purchase getPurchase(String username, String foodName, String dateTime) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        // parse string to ZonedDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss:nn VV");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);

        Optional<Purchase> purchase = purchaseRepo.findByFoodAndUserAndDateTime(food.get(), user.get(), zonedDateTime);

        return purchase.get();
    }

    public Purchase addPurchase(String username, String foodName, PurchaseDTO purchaseRequest) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        Purchase newPurchase = new Purchase(purchaseRequest.getQuantityBought(), purchaseRequest.getDateTime(), purchaseRequest.getExpiryDate(), food.get(), user.get());

        // Update quantity in container
        Storage storage = storageRepo.findByUsername(username).get();
        Container container = containerRepo.findByStorageAndFood(storage, food.get()).get();
        container.setQuantity(container.getQuantity() + newPurchase.getQuantityBought());
        containerRepo.save(container);

        //Save new purchase
        return purchaseRepo.save(newPurchase);
    }

    public Purchase updatePurchase(String username, String foodName, String dateTime, PurchaseDTO purchaseRequest) {
        Purchase purchase = getPurchase(username, foodName, dateTime);

        // Get change in quantity
        Double quantityDifference = purchaseRequest.getQuantityBought() - purchase.getQuantityBought();

        // Update consumption values
        purchase.setExpiryDate(purchaseRequest.getExpiryDate());
        purchase.setQuantityBought(purchaseRequest.getQuantityBought());

        //Update quantity in container
        Food food = purchase.getFood();
        Storage storage = storageRepo.findByUsername(username).get();
        Container container = containerRepo.findByStorageAndFood(storage, food).get();
        container.setQuantity(container.getQuantity() + quantityDifference);
        containerRepo.save(container);

        // Save updated purchase
        return purchaseRepo.save(purchase);
    }
}
