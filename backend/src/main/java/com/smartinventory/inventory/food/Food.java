package com.smartinventory.inventory.food;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.smartinventory.inventory.container.Container;

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
public class Food {
    @Id
    @SequenceGenerator(name = "food_sequence", sequenceName = "food_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "food_sequence")
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "food",
                orphanRemoval = true,
                cascade = CascadeType.ALL)
    private List<Container> containers;
}
