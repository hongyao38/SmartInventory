package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventory.inventory.container.ContainerRepository;
import com.smartinventory.inventory.container.ContainerService;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodDTO;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.food.FoodService;



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
    void addNewFood_newFood_SavedFood() {

        //Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);

        Food food = new Food("Butter");
        //mock optional food
        Optional<Food>  foodOptional = Optional.empty();

        //mock findByFoodName operation
        when(foodRepo.findByName(any(String.class))).thenReturn(foodOptional);
        //when(foodRepo.findByName("Butter")).thenReturn(foodOptional);
        //mock save operation
        when(foodRepo.save(any(Food.class))).thenReturn(food);
        //when(foodRepo.save(food)).thenReturn(food);

        //assert
        FoodDTO foodDTO = new FoodDTO("Butter");
        Food savedFood = foodService.addNewFood(foodDTO);
        assertEquals(food, savedFood);

        // verify
        verify(foodRepo).findByName(food.getName());
        verify(foodRepo).save(any(Food.class));
    }

    @Test
    void addFood_SameName_ReturnException() {
        //Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food food = new Food("Butter"); //added
        FoodDTO foodDTO = new FoodDTO(food.getName());

        //mock optional food
        Optional<Food>  foodOptional = Optional.of(food);

        //mock findByFoodName operation
        when(foodRepo.findByName(any(String.class))).thenReturn(foodOptional);

        //assert
        try {
            foodService.addNewFood(foodDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Butter already exists!");
        }

        // verify
        verify(foodRepo).findByName(food.getName());
    }
    
    
}
