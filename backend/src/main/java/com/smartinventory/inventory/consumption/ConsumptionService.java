package com.smartinventory.inventory.consumption;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
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
public class ConsumptionService {
    
    private final ConsumptionRepository consumptionRepo;
    private final FoodRepository foodRepo;
    private final AppUserRepository userRepo;
    private final ContainerRepository containerRepo;
    private final StorageRepository storageRepo;

    public List<Consumption> getAllUserConsumptionsFromFood(String username, String foodName) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        List<Consumption> consumptions = consumptionRepo.findByFoodAndUser(food.get(), user.get());

        if (consumptions == null) {
            consumptions = new ArrayList<Consumption>();
        }

        return consumptions;
    }

    public Consumption getConsumption(String username, String foodName, String dateTime) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        // parse string to ZonedDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss:nn VV");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);

        Optional<Consumption> consumption = consumptionRepo.findByFoodAndUserAndDateTime(food.get(), user.get(), zonedDateTime);

        return consumption.get();
    }

    public Consumption addConsumption(String username, String foodName, ConsumptionDTO consumptionRequest) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        Consumption newConsumption = new Consumption(consumptionRequest.getQuantity(), consumptionRequest.getDate(), food.get(), user.get());

        // Update quantity in container
        Storage storage = storageRepo.findByUsername(username).get();
        Container container = containerRepo.findByStorageAndFood(storage, food.get()).get();
        container.setQuantity(container.getQuantity() - newConsumption.getQuantity());
        containerRepo.save(container);

        // Save new consumption
        return consumptionRepo.save(newConsumption);
    }

    public Consumption updateConsumption(String username, String foodName, String dateTime, ConsumptionDTO consumptionRequest) {
        Consumption consumption = getConsumption(username, foodName, dateTime);

        // Get change in quantity
        Double quantityDifference = consumptionRequest.getQuantity() - consumption.getQuantity();

        // Update consumption values
        consumption.setDateTime(consumptionRequest.getDate());
        consumption.setQuantity(consumptionRequest.getQuantity());

        //Update quantity in container
        Food food = consumption.getFood();
        Storage storage = storageRepo.findByUsername(username).get();
        Container container = containerRepo.findByStorageAndFood(storage, food).get();
        container.setQuantity(container.getQuantity() - quantityDifference);
        containerRepo.save(container);

        // Save updated consumption
        return consumptionRepo.save(consumption);
    }

    


}
