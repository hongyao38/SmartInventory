package com.smartinventory.inventory.block;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartinventory.inventory.storage.Storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    @Id
    @SequenceGenerator(name = "block_sequence", sequenceName = "block_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "block_sequence")
    private Long id;
    
    @NotNull
    private Integer i;

    @NotNull
    private Integer j;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="storage_id",
                referencedColumnName = "id")
    private Storage storage;

    public Block(Integer i, Integer j, Storage storage) {
        this.i = i;
        this.j = j;
        this.storage = storage;
    }
}
