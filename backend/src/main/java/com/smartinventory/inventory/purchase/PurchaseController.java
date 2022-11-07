package com.smartinventory.inventory.purchase;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
    private final PurchaseService purchaseService;

    //Get a list of all purchases according to food and user
    @GetMapping("/{username}/purchases/{foodName}")
    public List<Purchase> getAllUserPurchasesFromFood(@PathVariable("username") String username, 
                                                        @PathVariable("foodName") String foodName) {
        return purchaseService.getAllUserPurchasesFromFood(username, foodName);
    }

    //Get a specific purchase for user
    @GetMapping("/{username}/{foodName}/purchases/{dateTime}")
    public Purchase getPurchase(@PathVariable("username") String username, 
                                @PathVariable("foodName") String foodName,
                                @PathVariable("dateTime") String dateTime) {
        return purchaseService.getPurchase(username, foodName, dateTime);
    }

    //Add new purchase
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{username}/purchases/{foodName}")
    public Purchase addPurchase(@PathVariable("username") String username,
                                @PathVariable("foodName") String foodName,
                                @Valid @RequestBody PurchaseDTO purchaseRequest) {
        return purchaseService.addPurchase(username, foodName, purchaseRequest);
    }

    //Update consumption
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{username}/purchases/{foodName}/{dateTime}")
    public Purchase updatePurchase(@PathVariable("username") String username,
                                        @PathVariable("foodName") String foodName,
                                        @PathVariable("dateTime") String dateTime,
                                        @Valid @RequestBody PurchaseDTO purchaseRequest) {
        return purchaseService.updatePurchase(username, foodName, dateTime, purchaseRequest);
    }
}
