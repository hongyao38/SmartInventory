package com.smartinventory.inventory.purchase;

import java.util.List;

import javax.validation.Valid;

import com.smartinventory.exceptions.inventory.FoodExistsException;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PurchaseController {
    private PurchaseService purchaseService;
    private FoodService foodService;

    @GetMapping("/purchases")
    public List<Purchase> getAllFood(){
        return purchaseService.listPurchases();
    }

    @GetMapping("/food/{foodId}/purchases")
    public List<Purchase> getPurchasesByFood(@PathVariable (value = "foodId") Long foodId){
        return purchaseService.listPurchaseByFood(foodId);
    }

    @GetMapping("/food/{foodId}/purchases/{purchaseId}")
    public Purchase getPurchase(@PathVariable (value = "purchaseId") Long purchaseId){
        return purchaseService.getPurchase(purchaseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/food/{foodId}/purchases")
    public Purchase addPurchase(@PathVariable (value = "foodId") Long foodId,
                                @Valid @RequestBody Purchase purchase) {
        Food food = foodService.getFood(foodId);
        purchase.setFood(food);
        
        return purchaseService.addPurchase(purchase);
    }

    @PutMapping("/food/{foodId}/purchases/{purchaseId}")
    public Purchase updatePurchase(@PathVariable (value = "purchaseId") Long purchaseId,
                                @Valid @RequestBody Purchase newPurchase){
        return purchaseService.updatePurchase(purchaseId, newPurchase);
    }

    @DeleteMapping("/food/{foodId}/purchases/{purchaseId}")
    public void deletePurchase(@PathVariable (value = "purchaseId") Long purchaseId){
        try{
            purchaseService.deletePurchase(purchaseId);
         }catch(EmptyResultDataAccessException e) {
            
         }
    }
}
