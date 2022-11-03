package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.smartinventory.exceptions.inventory.FoodExistsException;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.container.ContainerService;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.food.FoodService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepo;

    @InjectMocks
    private FoodService foodService;

    @Mock
    private ContainerRepository containerRepo;

    @InjectMocks
    private ContainerService containerService;

    @Test
    void addFood_newFood_SavedFood() {

        Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);

        //mock optional food
        Optional<Food>  foodOptional = Optional.empty();

        //mock findByFoodName operation
        when(foodRepo.findByFoodName(any(String.class))).thenReturn(foodOptional);
        
        //mock save operation
        when(foodRepo.save(any(Food.class))).thenReturn(food);

        //assert
        Food savedFood = foodService.addFood(food);
        assertEquals(food, savedFood);

        // verify
        verify(foodRepo).findByFoodName(food.getFoodName());
        verify(foodRepo).save(food);
    }

    @Test
    void addFood_SameName_ReturnException() {
        Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);

        //mock optional food
        Optional<Food>  foodOptional = Optional.of(food);

        //mock findByFoodName operation
        when(foodRepo.findByFoodName(any(String.class))).thenReturn(foodOptional);

        //assert
        try {
            foodService.addFood(food);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Butter already exists!");
        }

        // verify
        verify(foodRepo).findByFoodName(food.getFoodName());
    }
    
    @Test
    void updateFood_foodExists_SavedFood() {
        Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food updatedFood = new Food((long) 1, "Butter", "Dairy", 3.0, null, null, null);

        //mock optional food
        Optional<Food>  foodOptional = Optional.of(food);

        //mock findById operation
        when(foodRepo.findById(any(Long.class))).thenReturn(foodOptional);
        when(foodRepo.save(any(Food.class))).thenReturn(updatedFood);


        //assert
        Food toAssert = foodService.updateFood((long)1, updatedFood);
        assertEquals(updatedFood, toAssert);

        //verify
        verify(foodRepo).findById(food.getId());
        verify(foodRepo).save(food);
    }
    
    
    @Test
    void updateFood_FoodNotFound_ThrowException() {
        Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food updatedFood = new Food((long) 1, "Butter", "Dairy", 3.0, null, null, null);

        //mock optional food
        Optional<Food>  foodOptional = Optional.empty();
        when(foodRepo.findById(any(Long.class))).thenReturn(foodOptional);

        //assert
        try {
            foodService.updateFood((long)1, updatedFood);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Butter not found!");
        }

        //verify
        verify(foodRepo).findById(food.getId());
    }
}
