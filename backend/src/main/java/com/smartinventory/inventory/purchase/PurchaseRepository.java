package com.smartinventory.inventory.purchase;

import java.util.Date;
import java.util.List;

import com.smartinventory.inventory.food.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, PurchaseId>{
    //Create, Read, Update, Delete
    List<Purchase> findByPurchaseId_DatePurchased(Date datePurchased);
    List<Purchase> findByPurchaseId_Food(Food food);
    List<Purchase> findByExpiryDate(Date expiryDate);
}
