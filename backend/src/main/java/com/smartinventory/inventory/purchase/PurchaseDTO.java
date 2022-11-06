package com.smartinventory.inventory.purchase;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseDTO {
    @NotNull
    private Double quantityBought;

    @NotNull
    private ZonedDateTime dateTime;

    @NotNull
    private ZonedDateTime expiryDate;
}
