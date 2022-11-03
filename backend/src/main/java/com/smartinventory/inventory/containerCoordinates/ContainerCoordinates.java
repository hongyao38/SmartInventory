package com.smartinventory.inventory.containerCoordinates;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.storage.Storage;

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
public class ContainerCoordinates {

    @Id
    @SequenceGenerator(name = "container_coords_sequence", sequenceName = "container_coords_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "container_coords_sequence")
    private Long id;
    
    @NotNull
    private Container container;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "storageId",
                referencedColumnName = "storageId")
    private Storage storage;
}
