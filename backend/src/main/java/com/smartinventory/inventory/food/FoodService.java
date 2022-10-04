package com.smartinventory.inventory.food;

import java.util.List;
import com.smartinventory.exceptions.inventory.FoodNotFoundException;
import com.smartinventory.exceptions.inventory.FoodExistsException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FoodService {
    
    private final FoodRepository foodRepo;

    public List<Food> listFood() {
        return foodRepo.findAll();
    }

    public List<Food> listFoodbyCategory(String category) {
        return foodRepo.findByCategory(category);
    }

    public Food getFood(Long foodId) {
        String foodName = foodRepo.findById(foodId).get().getFoodName();
        return getFood(foodName);
    }

    public Food getFood(String foodName) throws FoodNotFoundException {
        return (foodRepo.findByFoodName(foodName)).orElseThrow(() -> new FoodNotFoundException(foodName));
    }

    //add new food
    public Food addFood(Food food) throws FoodExistsException {
        if (foodRepo.findById(food.getFoodId()) != null) {
            return null;
        }
        return foodRepo.save(food);
    }

    public Food updateFood(Long foodId, Food newFood) {
        return foodRepo.findById(foodId).map(food -> {food.setCurrentQuantity(newFood.getCurrentQuantity());
            return foodRepo.save(food);
        }).orElse(null);
    }

    public Food updateCurrentQuantity(Long foodId, Double quantity) {
        return foodRepo.findById(foodId).map(food -> {food.setCurrentQuantity(quantity);
            return foodRepo.save(food);
        }).orElse(null);
    }

    public void deleteFood(Long foodId) {
        foodRepo.deleteById(foodId);
    }

    public void deleteFood(String foodName) {
        foodRepo.deleteByfoodName(foodName);;
    }
}
