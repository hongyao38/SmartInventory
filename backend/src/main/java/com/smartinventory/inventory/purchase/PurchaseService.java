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
        return purchases.findByFood(food);
    }

    public List<Purchase> listPurchaseByDatePurchased(Date datePurchased) {
        return purchases.findByDateBought(datePurchased);
    }

    public Optional<Purchase> getPurchase(Long id) {
        if (purchases.findById(id).isEmpty()) {
            return null;
        }
        return purchases.findById(id);
    }

    //add new purchase
    // will automatically update total quantity in food
    public Purchase addPurchase(Purchase purchase) {
        //Finding the food from the purchase
        Food food = purchase.getFood();

        if (foodRepo.findById(food.getFoodId()).isEmpty()) {
            return null;
        }
        Double newQuantity = food.getCurrentQuantity() + purchase.getAmountBought();
        foodService.updateCurrentQuantity(food.getFoodId(), newQuantity);

        return purchases.save(purchase);
    }

    //edit amount bought
    public Purchase updatePurchase(Long purchaseId, Purchase newPurchase) {
        if (purchases.findById(purchaseId).get().getAmountBought() != newPurchase.getAmountBought()) {
            Food food = newPurchase.getFood();
            Double newQuantity = food.getCurrentQuantity() + newPurchase.getAmountBought();
            foodService.updateCurrentQuantity(food.getFoodId(), newQuantity);
        }
        return purchases.findById(purchaseId).map(purchase -> {newPurchase.getAmountBought();
            newPurchase.getDateBought();
            newPurchase.getExpiryDate();
            return purchases.save(purchase);
        }).orElse(null);
    }

    public void deletePurchase(Purchase purchase) {
        purchases.deleteById(purchase.getPurchaseId());
    }
}
