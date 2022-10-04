package com.smartinventory.inventory.purchase;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.smartinventory.exceptions.inventory.FoodExistsException;
import com.smartinventory.exceptions.inventory.FoodNotFoundException;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchases")
    public Purchase addPurchase(@Valid @RequestBody Purchase purchase) {
        if (purchaseService.addPurchase(purchase) == null) {
            throw new FoodExistsException(purchase.getPurchaseId().getFood().getFoodName());
        }
        return purchaseService.addPurchase(purchase);
    }

    @PutMapping("/purchases/{foodid}/{purchaseDate}")
    public Purchase updatePurchase(@PathVariable (value = "foodid") Long foodid,
                                @PathVariable (value = "purchaseDate") Date purchaseDate, 
                                @Valid @RequestBody Purchase newPurchase){
        return purchaseService.updatePurchase(newPurchase);
    }

    @DeleteMapping("/purchases/{foodId}/{purchaseDate")
    public void deleteFood(@PathVariable (value = "foodid") Long foodid,
                            @PathVariable (value = "purchaseDate") Date purchaseDate, 
                            @Valid @RequestBody Purchase purchase){
        try{
            purchaseService.deletePurchase(purchase);
         }catch(EmptyResultDataAccessException e) {
            
         }
    }
}
