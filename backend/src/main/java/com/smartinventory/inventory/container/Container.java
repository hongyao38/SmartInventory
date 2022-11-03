package com.smartinventory.inventory.container;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.storage.Storage;

import org.hibernate.annotations.Formula;
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
// @JsonComponent
public class Container {
    
    @Id
    @SequenceGenerator(name = "container_sequence", sequenceName = "container_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "container_sequence")
    private Long containerId;

    @NotNull
    private Double capacity;

    @NotNull
    private Integer row;

    @NotNull
    private Integer col;

    @NotNull
    private Double currentQuantity;

    @NotNull
    private Double percentageFilled;

    // @OneToOne
    // @JoinColumn(name = "foodId", 
    //             referencedColumnName = "id")    
    private Food food;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="storageId",
                referencedColumnName = "storageId")
    private Storage storage;

    public Container(Double capacity, Integer row, Integer col, Storage storage) {
        this.capacity = capacity;
        this.row = row;
        this.col = col;
        this.storage = storage;
    }
}
