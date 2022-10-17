package com.smartinventory.inventory.purchase;

import java.time.LocalDate;
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

    public List<Purchase> listPurchaseByFood(Long foodId) {
        return purchases.findByFoodId(foodId);
    }

    public List<Purchase> listPurchaseByDatePurchased(LocalDate datePurchased) {
        return purchases.findByDateBought(datePurchased);
    }

    public Purchase getPurchase(Long id) {
        if (purchases.findById(id).isEmpty()) {
            return null;
        }
        return purchases.findById(id).get();
    }

    //add new purchase
    // will automatically update total quantity in food
    public Purchase addPurchase(Purchase purchase) {
        //Finding the food from the purchase
        Food food = purchase.getFood();

        Double newQuantity = food.getCurrentQuantity() + purchase.getAmountBought();

        foodService.updateCurrentQuantity(food.getId(), newQuantity);

        return purchases.save(purchase);
    }

    //edit amount bought
    public Purchase updatePurchase(Long purchaseId, Purchase newPurchase) {

        Purchase currentPurchase = purchases.findById(purchaseId).get();

        double currentPurchaseAmt = currentPurchase.getAmountBought();

        Food food = currentPurchase.getFood();

        Double newQuantity = food.getCurrentQuantity() + (newPurchase.getAmountBought() - currentPurchaseAmt);
        foodService.updateCurrentQuantity(food.getId(), newQuantity);

        currentPurchase.setAmountBought(newPurchase.getAmountBought());
        currentPurchase.setExpiryDate(newPurchase.getExpiryDate());
        currentPurchase.setDateBought(newPurchase.getDateBought());
        return purchases.save(currentPurchase);
    }

    public void deletePurchase(Long purchaseId) {
        purchases.deleteById(purchaseId);
    }
}
