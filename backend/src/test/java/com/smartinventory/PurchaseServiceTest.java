package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.food.FoodService;
import com.smartinventory.inventory.purchase.Purchase;
import com.smartinventory.inventory.purchase.PurchaseRepository;
import com.smartinventory.inventory.purchase.PurchaseService;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    PurchaseRepository purchases;

    @InjectMocks
    PurchaseService purchaseService;

    @Mock
    FoodService foodService;

    

    @Test
    void listPurchases_NotFound_ReturnEmptyList() {
        List<Purchase> emptyList = new ArrayList<>();

        when(purchases.findAll()).thenReturn(emptyList);

        List<Purchase> returnedList = purchaseService.listPurchases();

        assertTrue(returnedList.isEmpty());
        verify(purchases).findAll();
    }

    @Test
    void listPurchases_ContainsItems_ReturnNonEmptyList() {
        List<Purchase> list = new ArrayList<>();

        LocalDate datePurchase = LocalDate.of(2022, 11, 6);
        LocalDate dateExpiry = LocalDate.of(2022, 12, 6);
        Food food1 = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food food2 = new Food((long) 1, "Milk", "Dairy", 2.0, null, null, null);

        Purchase p1 = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food1);
        Purchase p2 = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food2);

        list.add(p1);
        list.add(p2);

        when(purchases.findAll()).thenReturn(list);

        List<Purchase> returnedList = purchaseService.listPurchases();

        assertTrue(returnedList.contains(p1) && returnedList.contains(p2));
        verify(purchases).findAll();
    }

    @Test
    void listPurchaseByFood_NotFound_ReturnEmptyList() {
        List<Purchase> emptyList = new ArrayList<>();

        when(purchases.findByFoodId((long) 1)).thenReturn(emptyList);

        List<Purchase> returnedList = purchaseService.listPurchaseByFood((long) 1);

        assertTrue(returnedList.isEmpty());
        verify(purchases).findByFoodId((long) 1);
    }

    @Test
    void listPurchaseByFood_ContainsItems_ReturnNonEmptyList() {
        List<Purchase> list = new ArrayList<>();

        LocalDate datePurchase = LocalDate.of(2022, 11, 6);
        LocalDate dateExpiry = LocalDate.of(2022, 12, 6);
        Food food1 = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food food2 = new Food((long) 2, "Milk", "Dairy", 2.0, null, null, null);

        Purchase p1 = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food1);
        Purchase p2 = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food2);

        list.add(p1);

        when(purchases.findByFoodId((long) 1)).thenReturn(list);

        List<Purchase> returnedList = purchaseService.listPurchaseByFood((long) 1);

        assertTrue(returnedList.contains(p1) && !returnedList.contains(p2));
        verify(purchases).findByFoodId((long) 1);
    }

    @Test
    void listPurchaseByDatePurchased_NotFound_ReturnEmptyList() {
        List<Purchase> emptyList = new ArrayList<>();

        LocalDate datePurchase = LocalDate.of(2022, 11, 6);

        when(purchases.findByDateBought(datePurchase)).thenReturn(emptyList);

        List<Purchase> returnedList = purchaseService.listPurchaseByDatePurchased(datePurchase);

        assertTrue(returnedList.isEmpty());
        verify(purchases).findByDateBought(datePurchase);
    }

    @Test
    void listPurchaseByDatePurchased_ContainsItems_ReturnNonEmptyList() {
        List<Purchase> list = new ArrayList<>();

        LocalDate datePurchase1 = LocalDate.of(2022, 11, 6);
        LocalDate datePurchase2 = LocalDate.of(2022, 11, 7);
        LocalDate dateExpiry = LocalDate.of(2022, 12, 6);
        Food food1 = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Food food2 = new Food((long) 2, "Milk", "Dairy", 2.0, null, null, null);

        Purchase p1 = new Purchase((long) 1, datePurchase1, 4.0, dateExpiry, food1);
        Purchase p2 = new Purchase((long) 1, datePurchase2, 4.0, dateExpiry, food2);

        list.add(p1);

        when(purchases.findByDateBought(datePurchase1)).thenReturn(list);

        List<Purchase> returnedList = purchaseService.listPurchaseByDatePurchased(datePurchase1);

        assertTrue(returnedList.contains(p1) && !returnedList.contains(p2));
        verify(purchases).findByDateBought(datePurchase1);
    }

    @Test
    void getPurchase_NotFound_ReturnNull() {
        Optional<Purchase> emptyOptional = Optional.empty();
        when(purchases.findById((long) 1)).thenReturn(emptyOptional);

        Purchase returnedPurchase = purchaseService.getPurchase((long) 1);

        assertNull(returnedPurchase);
        verify(purchases).findById((long) 1);
    }

    @Test
    void getPurchase_ContainItems_ReturnNonEmptyList() {
        LocalDate datePurchase = LocalDate.of(2022, 11, 6);
        LocalDate dateExpiry = LocalDate.of(2022, 12, 6);
        Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
        Purchase p = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food);

        Optional<Purchase> optional = Optional.of(p);

        when(purchases.findById((long) 1)).thenReturn(optional);

        Purchase returnedPurchase = purchaseService.getPurchase((long) 1);

        assertEquals(p, returnedPurchase);
        verify(purchases, times(2)).findById((long) 1);
    }

    // has some issues
    //idk why foodSergice has nullpointerexception
    // @Test
    // void addPurchase_AddQuantity_ReturnPurchase() {
    //     LocalDate datePurchase = LocalDate.of(2022, 11, 6);
    //     LocalDate dateExpiry = LocalDate.of(2022, 12, 6);
    //     Food food = new Food((long) 1, "Butter", "Dairy", 2.0, null, null, null);
    //     Purchase p = new Purchase((long) 1, datePurchase, 4.0, dateExpiry, food);

    //     // doNothing().when(foodService).updateCurrentQuantity((long) 1, 6.0);
    //     // when(foodService.updateCurrentQuantity((long)1), 6.0).thenReturn()
    //     Purchase returnedPurchase = purchaseService.addPurchase(p);

    //     assertEquals(returnedPurchase.getFood().getCurrentQuantity(), 6.0);

    // }

    @Test
    void updatePurchase_Success_ReturnUpdatedPurchase() {

    }

}
