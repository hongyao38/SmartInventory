package com.smartinventory.inventory.consumption;

import java.util.Date;
import java.util.List;

import com.smartinventory.inventory.food.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRespository extends JpaRepository<Consumption, Long>{
    //Create, Read, Update, Delete
    List<Consumption> findByDateConsumed(Date dateConsumed);
    List<Consumption> findByFood(Food food);
}
