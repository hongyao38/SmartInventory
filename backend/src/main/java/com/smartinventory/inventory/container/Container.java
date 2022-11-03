package com.smartinventory.inventory.container;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

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
public class Container {

    @Id
    @SequenceGenerator(name = "container_sequence", sequenceName = "container_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "container_sequence")
    private Long id;

    @NotNull
    private Double capacity;

    @NotNull
    private Integer i;

    @NotNull
    private Integer j;

    @NotNull
    private Double quantity;

    public Container(Double capacity, Integer i, Integer j) {
        this.capacity = capacity;
        this.i = i;
        this.j = j;
    }
}
