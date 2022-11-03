package com.smartinventory.inventory.food;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.smartinventory.exceptions.inventory.FoodNotFoundException;

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

    @GetMapping("/food/{foodName}")
    public Food getFood(@PathVariable String foodName){
        return foodService.getFood(foodName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/food")
    public Food addFood(@Valid @RequestBody FoodDTO foodRequest) {
        return foodService.addFood(foodRequest);
    }

    @DeleteMapping("/food/{foodName}")
    public void deleteFood(@PathVariable String foodName){
        foodService.deleteFood(foodName);
    }
}
