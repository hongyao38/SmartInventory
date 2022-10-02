package com.smartinventory.inventory.food;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    
}
