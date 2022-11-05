package com.smartinventory.inventory.food;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>  {
    Optional<Food> findByName(String name);
}
