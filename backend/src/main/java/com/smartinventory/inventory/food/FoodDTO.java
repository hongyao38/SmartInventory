package com.smartinventory.inventory.food;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    
    @NotNull
    private String name;

    @NotNull
    private Double quantity;

    public FoodDTO(String name) {
        this.name = name;
    }
}
