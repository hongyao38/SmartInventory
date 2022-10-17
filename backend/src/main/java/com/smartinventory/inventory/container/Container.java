package com.smartinventory.inventory.container;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.smartinventory.inventory.consumption.Consumption;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.purchase.Purchase;

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
    private Long containerId;

    @NotNull
    private Double capacity;

    @NotNull
    private Double threshold;

    @NotNull
    private Double percentageFilled;

    @OneToOne(mappedBy = "container",
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    @NotNull
    private Food food;

    // @ManyToOne(mappedBy = "food",
    //     orphanRemoval = true,
    //     cascade = CascadeType.ALL)
    // private List<Consumption> consumptions;
}
