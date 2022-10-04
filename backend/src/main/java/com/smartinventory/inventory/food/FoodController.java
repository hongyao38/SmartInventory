package com.smartinventory.inventory.food;

import java.util.List;

import javax.validation.Valid;

import com.smartinventory.exceptions.inventory.FoodExistsException;
import com.smartinventory.exceptions.inventory.FoodNotFoundException;

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
public class FoodController {
    private FoodService foodService;

    @GetMapping("/food")
    public List<Food> getAllFood(){
        return foodService.listFood();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/food")
    public Food addFood(@Valid @RequestBody Food food) {
        if (foodService.addFood(food) == null) {
            throw new FoodExistsException(food.getFoodName());
        }
        return foodService.addFood(food);
    }

    @PutMapping("/food/{foodId}")
    public Food updateFood(@PathVariable Long foodId, @Valid @RequestBody Food newFood){
        Food food = foodService.updateFood(foodId, newFood);
        if(food == null) throw new FoodNotFoundException(newFood.getFoodName());
        return food;
    }

    @DeleteMapping("/food/{foodId}")
    public void deleteFood(@PathVariable Long foodId){
        try{
            foodService.deleteFood(foodId);
         }catch(EmptyResultDataAccessException e) {
            throw new FoodNotFoundException(foodService.getFood(foodId).getFoodName());
         }
    }
}
