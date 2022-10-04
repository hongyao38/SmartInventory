package com.smartinventory.inventory.purchase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.smartinventory.inventory.food.Food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchaseId implements Serializable{

    @Column
    @NotNull
    private Date datePurchased;
    
    @ManyToOne
    @JoinColumn(name="foodId")
    private Food food;
}
