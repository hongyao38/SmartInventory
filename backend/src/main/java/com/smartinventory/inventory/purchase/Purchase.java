package com.smartinventory.inventory.purchase;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartinventory.appuser.AppUser;
import com.smartinventory.inventory.food.Food;

import org.springframework.boot.jackson.JsonComponent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class Purchase {
    @Id
    @SequenceGenerator(name = "purchase_sequence", sequenceName = "purchase_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchase_sequence")
    private Long id;

    @NotNull
    private Double quantityBought;

    // purchased dateTime
    @NotNull
    private ZonedDateTime dateTime;

    @NotNull
    private ZonedDateTime expiryDate;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="food_Id",
                referencedColumnName = "id")
    private Food food;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "username",
                referencedColumnName = "id")
    private AppUser user;

    public Purchase(Double quantityBought, ZonedDateTime dateTime, ZonedDateTime expiryDate, Food food, AppUser user) {
        this.quantityBought = quantityBought;
        this.dateTime = dateTime;
        this.expiryDate = expiryDate;
        this.food = food;
        this.user = user;
    }
}
