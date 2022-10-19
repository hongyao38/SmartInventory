package com.smartinventory.inventory.storage;

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

import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.food.Food;

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
@JsonComponent
public class Storage {
    
    @Id
    @SequenceGenerator(name = "storage_sequence", sequenceName = "storage_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "storage_sequence")
    private Long storageId;

    //total number of boxes available
    @NotNull
    private int size;

    //total number of boxes left
    private int storageLeft;

    @OneToMany(mappedBy = "storage",
                orphanRemoval = true,
                cascade = CascadeType.ALL)
    private List<Container> containers;
}
