package com.smartinventory.inventory.food;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{
    //Create, Read, Update, Delete
    List<Food> findByCategory(String category);
    Food findByFoodName(String foodName);
    void deleteByfoodName(String foodName);
    
}
