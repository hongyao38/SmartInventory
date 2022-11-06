package com.smartinventory.inventory.block;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlockDTO {
    
    @NotNull
    private Integer i;

    @NotNull
    private Integer j;
}
