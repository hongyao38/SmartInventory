package com.smartinventory.inventory.purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.FoodNotFoundException;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodRepository;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PurchaseService {
    
    private final PurchaseRepository purchases;
    private final FoodRepository foodRepo;
    private final FoodService foodService;

    public List<Purchase> listPurchases() {
        return purchases.findAll();
    }

    public List<Purchase> listPurchaseByFood(Food food) {
        return purchases.findByPurchaseId_Food(food);
    }

    public List<Purchase> listPurchaseByDatePurchased(Date datePurchased) {
        return purchases.findByPurchaseId_DatePurchased(datePurchased);
    }

    public Optional<Purchase> getPurchase(PurchaseId purchaseId) {
        if (purchases.findById(purchaseId).isEmpty()) {
            return null;
        }
        return purchases.findById(purchaseId);
    }

    //add new purchase
    // will automatically update total quantity in food
    public Purchase addPurchase(Purchase purchase) {
        //Finding the food from the purchase
        Food food = purchase.getPurchaseId().getFood();

        if (foodRepo.findById(food.getFoodId()).isEmpty()) {
            return null;
        }
        Double newQuantity = food.getCurrentQuantity() + purchase.getAmountBought();
        foodService.updateCurrentQuantity(food.getFoodId(), newQuantity);

        return purchases.save(purchase);
    }

    public Purchase updatePurchase(Purchase newPurchase) {
        return purchases.save(newPurchase);
    }

    public void deletePurchase(Purchase purchase) {
        purchases.deleteById(purchase.getPurchaseId());
    }
}
