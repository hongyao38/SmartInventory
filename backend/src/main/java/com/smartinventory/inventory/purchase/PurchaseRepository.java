package com.smartinventory.inventory.purchase;

import java.util.Date;
import java.util.List;

import com.smartinventory.inventory.food.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    //Create, Read, Update, Delete
    List<Purchase> findByDateBought(Date dateBought);
    List<Purchase> findByFood(Food food);
    List<Purchase> findByExpiryDate(Date expiryDate);
}
