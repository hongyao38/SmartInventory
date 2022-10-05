package com.smartinventory.inventory.consumption;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsumptionService {
    
    private final ConsumptionRespository consumptions;
    private final FoodRepository foodRepo;
    private final FoodService foodService;

    public List<Consumption> listconsumptions() {
        return consumptions.findAll();
    }

    public List<Consumption> listConsumptionsByFood(Food food) {
        return consumptions.findByFood(food);
    }

    public List<Consumption> listConsumptionByDateConsumed(Date dateConsumed) {
        return consumptions.findByDateConsumed(dateConsumed);
    }

    public Optional<Consumption> getConsumption(Long consumptionId) {
        if (consumptions.findById(consumptionId).isEmpty()) {
            return null;
        }
        return consumptions.findById(consumptionId);
    }

    //add new purchase
    // will automatically update total quantity in food
    public Consumption addConsumption(Consumption consumption) {
        //Finding the food from the purchase
        Food food = consumption.getFood();

        if (foodRepo.findById(food.getFoodId()).isEmpty()) {
            return null;
        }
        Double newQuantity = food.getCurrentQuantity() - consumption.getAmountConsumed();
        foodService.updateCurrentQuantity(food.getFoodId(), newQuantity);

        return consumptions.save(consumption);
    }

    //edit amount consumed
    public Consumption updateConsumption(Long consumptionId, Consumption newConsumption) {
        if (consumptions.findById(consumptionId).get().getAmountConsumed() != newConsumption.getAmountConsumed()) {
            Food food = newConsumption.getFood();
            Double newQuantity = food.getCurrentQuantity() - newConsumption.getAmountConsumed();
            foodService.updateCurrentQuantity(food.getFoodId(), newQuantity);
        }
        return consumptions.findById(consumptionId).map(consumption -> {newConsumption.getAmountConsumed();
            newConsumption.getDateConsumed();
            return consumptions.save(consumption);
        }).orElse(null);
    }

    public void deleteConsumption(Consumption consumption) {
        consumptions.deleteById(consumption.getConsumptionId());
    }
}
