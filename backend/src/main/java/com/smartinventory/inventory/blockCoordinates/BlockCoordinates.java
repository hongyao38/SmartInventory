package com.smartinventory.inventory.blockCoordinates;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartinventory.inventory.block.Block;
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
public class BlockCoordinates {
    @Id
    @SequenceGenerator(name = "block_coords_sequence", sequenceName = "block_coords_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "block_coords_sequence")
    private Long id;
    
    @NotNull
    @OneToMany(mappedBy = "storage",
                    orphanRemoval = true,
                    cascade = CascadeType.ALL)
    private List<Block> blocks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "storage_id",
                referencedColumnName = "id")
    private Storage storage;

    public BlockCoordinates(List<Block> blocks, Storage storage) {
        this.blocks = blocks;
        this.storage = storage;
    }

}
