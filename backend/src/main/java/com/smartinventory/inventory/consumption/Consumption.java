package com.smartinventory.inventory.consumption;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartinventory.appuser.AppUser;
import com.smartinventory.inventory.food.Food;

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
public class Consumption {
    @Id
    @SequenceGenerator(name = "consumption_sequence", sequenceName = "consumption_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "consumption_sequence")
    private Long id;

    @NotNull
    private Double quantity;

    @NotNull
    private ZonedDateTime dateTime;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="food",
                referencedColumnName = "name")
    private Food food;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user",
                referencedColumnName = "username")
    private AppUser user;

    public Consumption(Double quantity, ZonedDateTime dateTime, Food food, AppUser user) {
        this.quantity = quantity;
        this.dateTime = dateTime;
        this.food = food;
        this.user = user;
    }
}
