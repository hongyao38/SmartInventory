package com.smartinventory.inventory.purchase;

import java.util.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import com.smartinventory.inventory.food.Food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @NotNull
    private Date dateBought;

    @NotNull
    private Double amountBought;

    @NotNull
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name="foodId")
    private Food food;
}
