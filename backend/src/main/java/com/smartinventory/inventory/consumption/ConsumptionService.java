package com.smartinventory.inventory.consumption;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.appuser.AppUserRepository;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsumptionService {
    
    private final ConsumptionRepository consumptionRepo;
    private final FoodRepository foodRepo;
    private final AppUserRepository userRepo;

    public List<Consumption> getAllUserConsumptionsFromFood(String username, String foodName) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        return consumptionRepo.findByFoodAndUser(food.get(), user.get());
    }

    public Consumption getConsumption(String username, String foodName, String dateTime) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss VV");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);

        Optional<Consumption> consumption = consumptionRepo.findByFoodAndUserAndDateTime(food.get(), user.get(), zonedDateTime);

        return consumption.get();
    }

    public Consumption addConsumption(String username, String foodName, ConsumptionDTO consumptionRequest) {
        Optional<Food> food = foodRepo.findByName(foodName);
        Optional<AppUser> user = userRepo.findByUsername(username);

        Consumption newConsumption = new Consumption(consumptionRequest.getQuantity(), consumptionRequest.getDate(), food.get(), user.get());

        return newConsumption;
    }

    public Consumption updateConsumption(String username, String foodName, String dateTime, ConsumptionDTO consumptionRequest) {
        Consumption consumption = getConsumption(username, foodName, dateTime);

        consumption.setDateTime(consumptionRequest.getDate());
        consumption.setQuantity(consumptionRequest.getQuantity());

        return consumptionRepo.save(consumption);
    }

    


}
