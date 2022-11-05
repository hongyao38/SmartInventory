package com.smartinventory.inventory.consumption;

import java.util.List;

import com.smartinventory.appuser.AppUserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsumptionService {
    
    private final ConsumptionRepository consumptionRepo;
    private final FoodRepository foodRepo;
    private final AppUserRepository userRepo;

    public List<Consumption> getAllConsumptionsFromFood(String username, String foodName) {
        Optional<Food> food = foodRepo.findByFood
        return consumptionRepo.findByFoodAndUser(food, user);
    }
}
