package com.smartinventory.inventory.containerCoordinates;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartinventory.inventory.container.Container;
import com.smartinventory.inventory.storage.Storage;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "container_id",
                referencedColumnName = "id")
    private Container container;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "storage_id",
                referencedColumnName = "id")
    private Storage storage;

    public ContainerCoordinates(Container container, Storage storage) {
        this.container = container;
        this.storage = storage;
    }

    @Override
    public String toString() {
        return String.format("Coordinate: id = %d, container = i:%d j:%d, storage = %s", id, container.getI(), container.getJ(), storage.getUsername());
    }
}
