package com.smartinventory.inventory.storage;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.smartinventory.inventory.block.Block;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartinventory.inventory.container.Container;

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
    private Long id;

    @NotNull
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "storage",
                orphanRemoval = true,
                cascade = CascadeType.ALL)
    private List<Container> containers;

    @OneToMany(mappedBy = "storage", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Block> blocks;

    public Storage(String username) {
        this.username = username;
    }
}
