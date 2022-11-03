package com.smartinventory.inventory.container;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ContainerDTO {
    @NotNull
    private Double capacity;

    @NotNull
    private Integer i;

    @NotNull
    private Integer j;
}