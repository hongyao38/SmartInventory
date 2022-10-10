package com.smartinventory.inventory.consumption;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Consumption {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long consumptionId;

    @NotNull
    private Date dateConsumed;

    @NotNull
    private Double amountConsumed;

    @ManyToOne
    @JoinColumn(name="id")
    private Food food;
}
