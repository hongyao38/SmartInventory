package com.smartinventory.inventory.food;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.smartinventory.inventory.purchase.*;
import com.smartinventory.inventory.consumption.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @SequenceGenerator(name = "food_sequence", sequenceName = "food_sequence", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long foodId;

    @NotNull
    private String foodName;

    @NotNull
    private String category;

    @NotNull
    private double currentQuantity;

    @OneToMany(mappedBy = "food",
        orphanRemoval = true, 
        cascade = CascadeType.ALL)
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "food",
        orphanRemoval = true, 
        cascade = CascadeType.ALL)
    private List<Consumption> consumptions;
}
