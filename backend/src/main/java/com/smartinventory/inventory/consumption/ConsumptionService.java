package com.smartinventory.inventory.consumption;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.FoodNotFoundException;
import com.smartinventory.exceptions.inventory.InvalidConsumptionException;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsumptionService {
    
    private final ConsumptionRespository consumptions;
    private final FoodService foodService;

    public List<Consumption> listconsumptions() {
        return consumptions.findAll();
    }

    public List<Consumption> listConsumptionsByFood(Long foodId) {
        return consumptions.findByFoodId(foodId);
    }

    public List<Consumption> listConsumptionByDateConsumed(LocalDate dateConsumed) {
        return consumptions.findByDateConsumed(dateConsumed);
    }

    public Consumption getConsumption(Long consumptionId) {
        if (consumptions.findById(consumptionId).isEmpty()) {
            return null;
        }
        return consumptions.findById(consumptionId).get();
    }

    // //add new consumption
    // // will automatically update total quantity in food
    // public Consumption addConsumption(Consumption consumption) {
    //     //Finding the food from the purchase
    //     Food food = consumption.getFood();
        
    //     Double newQuantity = food.getCurrentQuantity() - consumption.getAmountConsumed();

    //     if (newQuantity < 0) {
    //         throw new InvalidConsumptionException("Current Quantity Insufficient");
    //     }

    //     foodService.updateCurrentQuantity(food.getId(), newQuantity);

    //     return consumptions.save(consumption);
    // }

    // //edit amount consumed
    // public Consumption updateConsumption(Long consumptionId, Consumption newConsumption) {
    //     Consumption currentConsumption = consumptions.findById(consumptionId).get();

    //     double currentConsumptionAmt = currentConsumption.getAmountConsumed();

    //     Food food = currentConsumption.getFood();
    //     Double newQuantity = food.getCurrentQuantity() - (newConsumption.getAmountConsumed() - currentConsumptionAmt);
    //     foodService.updateCurrentQuantity(food.getId(), newQuantity);

    //     currentConsumption.setAmountConsumed(newConsumption.getAmountConsumed());
    //     currentConsumption.setDateConsumed(newConsumption.getDateConsumed());
    //     return consumptions.save(currentConsumption);
    // }

    public void deleteConsumption(Long consumptionId) {
        consumptions.deleteById(consumptionId);
    }
}
