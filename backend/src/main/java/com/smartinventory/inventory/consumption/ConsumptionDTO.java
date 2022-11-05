package com.smartinventory.inventory.consumption;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsumptionDTO {
    @NotNull
    private Double quantity;

    @NotNull
    private ZonedDateTime date;
}
