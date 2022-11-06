package com.smartinventory.inventory.consumption;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

    private final ConsumptionService consumptionService;

    //Get a list of all consumptions according to food and user
    @GetMapping("/{username}/{foodName}")
    public List<Consumption> getAllUserConsumptionsFromFood(@PathVariable("username") String username, 
                                                            @PathVariable("foodName") String foodName) {
        return consumptionService.getAllUserConsumptionsFromFood(username, foodName);
    }

    //Get a specific consumption for user
    @GetMapping("/{username}/{foodName}/{dateTime}")
    public Consumption getConsumption(@PathVariable("username") String username, 
                                            @PathVariable("foodName") String foodName,
                                            @PathVariable("dateTime") String dateTime) {
        return consumptionService.getConsumption(username, foodName, dateTime);
    }

    //Add new consumption
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{username}/{foodName}")
    public Consumption addConsumption(@PathVariable("username") String username,
                                        @PathVariable("foodName") String foodName,
                                        @Valid @RequestBody ConsumptionDTO consumptionRequest) {
        return consumptionService.addConsumption(username, foodName, consumptionRequest);
    }

    //Update consumption
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{username}/{foodName}/{dateTime}")
    public Consumption updateConsumption(@PathVariable("username") String username,
                                        @PathVariable("foodName") String foodName,
                                        @PathVariable("dateTime") String dateTime,
                                        @Valid @RequestBody ConsumptionDTO consumptionRequest) {
        return consumptionService.updateConsumption(username, foodName, dateTime, consumptionRequest);
    }


}
