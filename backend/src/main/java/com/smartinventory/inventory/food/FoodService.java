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
    public Food addFood(FoodDTO foodRequest) throws FoodExistsException {
        // If food has already been added, throw exception
        if (foodRepo.findByFoodName(foodRequest.getFoodName()).isPresent()) {
            throw new FoodExistsException(foodRequest.getFoodName());
        }

        // Create Food object
        Food food = new Food(foodRequest.getFoodName(), foodRequest.getCategory());
        return foodRepo.save(food);
    }


    public void deleteFood(String foodName) {
        foodRepo.deleteByfoodName(foodName);
    }
}
