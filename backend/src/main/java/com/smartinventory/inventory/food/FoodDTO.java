package com.smartinventory.inventory.food;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDTO {
    
    @NotNull
    private String foodName;

    @NotNull
    private String category;
}
