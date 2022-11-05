package com.smartinventory.inventory.consumption;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;
import com.smartinventory.inventory.food.Food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long>{

    List<Consumption> findByFoodAndUser(Food food, AppUser user);
    Optional<Consumption> findByFoodAndUserAndDateTime(Food food, AppUser user, ZonedDateTime date);
}
