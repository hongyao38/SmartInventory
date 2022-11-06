package com.smartinventory.inventory.purchase;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.inventory.food.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

    List<Purchase> findByFoodAndUser(Food food, AppUser user);
    Optional<Purchase> findByFoodAndUserAndDateTime(Food food, AppUser user, ZonedDateTime date);
}
