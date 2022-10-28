package com.smartinventory.inventory.food;

import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.FoodNotFoundException;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.container.ContainerService;
import com.smartinventory.exceptions.inventory.FoodExistsException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FoodService {
    
    private final ContainerService containerService;
    private final FoodRepository foodRepo;
    private final ContainerRepository containerRepo;

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
        if (foodRepo.findByFoodName(food.getFoodName()).isPresent()) {
            throw new FoodExistsException(food.getFoodName());
        }
        return foodRepo.save(food);
    }

    public Food updateFood(Long foodId, Food newFood) {
        Optional<Food> food = foodRepo.findById(foodId);

        if (food.isEmpty()) {
            throw new FoodNotFoundException(newFood.getFoodName());
        }

        Food updatedFood = food.get();
        updatedFood.setCurrentQuantity(newFood.getCurrentQuantity());
        
        if (updatedFood.getContainer() != null) {
            containerService.updateContainer(updatedFood.getContainer(), updatedFood.getCurrentQuantity());
        }
        

        return foodRepo.save(updatedFood);
    }

    public Food updateCurrentQuantity(Long foodId, Double quantity) {
        Optional<Food> food = foodRepo.findById(foodId);

        if (food.isEmpty()) {
            return null;
        }

        Food updatedFood = food.get();
        updatedFood.setCurrentQuantity(quantity);

        if (updatedFood.getContainer() != null) {
            containerService.updateContainer(updatedFood.getContainer(), updatedFood.getCurrentQuantity());
        }
        return foodRepo.save(updatedFood);
    }

    public void deleteFood(Long foodId) {
        foodRepo.deleteById(foodId);
    }

    public void deleteFood(String foodName) {
        foodRepo.deleteByfoodName(foodName);;
    }
}
