package com.smartinventory.inventory.consumption;

import java.util.List;

import javax.validation.Valid;

import com.smartinventory.exceptions.inventory.FoodExistsException;
import com.smartinventory.exceptions.inventory.FoodNotFoundException;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ConsumptionController {
    private ConsumptionService consumptionService;
    private FoodService foodService;

    @GetMapping("/consumptions")
    public List<Consumption> getAllConsumptions(){
        return consumptionService.listconsumptions();
    }

    @GetMapping("/food/{foodId}/consumptions")
    public List<Consumption> getConsumptionsByFood(@PathVariable (value = "foodId") Long foodId){
        return consumptionService.listConsumptionsByFood(foodId);
    }

    @GetMapping("/food/{foodId}/consumptions/{consumptionId}")
    public Consumption getConsumption(@PathVariable (value = "consumptionId") Long consumptionId){
        return consumptionService.getConsumption(consumptionId);
    }


    // @ResponseStatus(HttpStatus.CREATED)
    // @PostMapping("/food/{foodId}/consumptions")
    // public Consumption addConsumption(@PathVariable (value = "foodId") Long foodId,
    //                                     @Valid @RequestBody Consumption consumption) {
        
    //     Food food = foodService.getFood(foodId);
    //     consumption.setFood(food);

    //     return consumptionService.addConsumption(consumption);
    // }

    // @PutMapping("/food/{foodId}/consumptions/{consumptionId}")
    // public Consumption updatePurchase(@PathVariable (value = "consumptionId") Long consumptionId,
    //                             @Valid @RequestBody Consumption newConsumption){
    //     return consumptionService.updateConsumption(consumptionId, newConsumption);
    // }

    @DeleteMapping("/food/{foodId}/consumptions/{consumptionId}")
    public void deletePurchase(@PathVariable (value = "consumptionId") Long consumptionId){
        try{
            consumptionService.deleteConsumption(consumptionId);
         }catch(EmptyResultDataAccessException e) {
            
         }
    }
}
