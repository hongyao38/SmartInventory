package com.smartinventory.inventory.food;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FoodService {
    
    private final FoodRepository foodRepo;

    /**
     * Creates a new listing for food if food has not yet been added by any user
     * If food already exists, return existing food
     * @param foodRequest
     * @return Food that has been saved
     */
    public Food addNewFood(FoodDTO foodRequest) {
        Optional<Food> food = foodRepo.findByName(foodRequest.getName());

        // If food already exists, get existing food
        if (food.isPresent()) {
            return food.get();
        }
        // Create new listing of food
        Food newFood = new Food(foodRequest.getName());
        return foodRepo.save(newFood);
    }
}
