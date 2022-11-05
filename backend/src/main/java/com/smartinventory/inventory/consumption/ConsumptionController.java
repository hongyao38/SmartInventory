package com.smartinventory.inventory.consumption;

import java.util.List;

import com.smartinventory.appuser.AppUser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    //Get a list of all consumptions according to food
    @GetMapping("/{username}/{foodName}")
    public List<Consumption> getAllUserConsumptionsFromFood(@PathVariable("username") String username, 
                                                            @PathVariable("foodName") String foodName) {
        return consumptionService.getAllUserConsumptionsFromFood(username, foodName);
    }


    //Add new consumption

    //Update consumption


}
