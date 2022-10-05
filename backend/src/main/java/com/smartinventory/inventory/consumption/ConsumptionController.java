package com.smartinventory.inventory.consumption;

import java.util.List;

import javax.validation.Valid;

import com.smartinventory.exceptions.inventory.FoodExistsException;

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

    @GetMapping("/consumptions")
    public List<Consumption> getAllConsumptions(){
        return consumptionService.listconsumptions();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/consumptions")
    public Consumption addConsumption(@Valid @RequestBody Consumption consumption) {
        if (consumptionService.addConsumption(consumption) == null) {
            throw new FoodExistsException(consumption.getFood().getFoodName());
        }
        return consumptionService.addConsumption(consumption);
    }

    @PutMapping("/consumptions/{consumptionId}")
    public Consumption updatePurchase(@PathVariable (value = "consumptionId") Long consumptionId,
                                @Valid @RequestBody Consumption newConsumption){
        return consumptionService.updateConsumption(consumptionId, newConsumption);
    }

    @DeleteMapping("/consumptions/{consumptionId}")
    public void deletePurchase(@PathVariable (value = "consumptionId") Long consumptionId,
                            @Valid @RequestBody Consumption consumption){
        try{
            consumptionService.deleteConsumption(consumption);
         }catch(EmptyResultDataAccessException e) {
            
         }
    }
}
