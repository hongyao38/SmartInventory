package com.smartinventory.inventory.purchase;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private LocalDate dateBought;

    @NotNull
    private Double amountBought;

    @NotNull
    private LocalDate expiryDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="foodId",
                referencedColumnName = "id")
    private Food food;
}
