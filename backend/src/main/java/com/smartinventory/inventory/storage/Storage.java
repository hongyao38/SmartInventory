package com.smartinventory.inventory.storage;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.smartinventory.inventory.container.Container;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;

import org.hibernate.annotations.TypeDef;
import org.hibernate.type.EnumType;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
// @TypeDef(
//     name = "shape_array",
//     typeClass = EnumArrayType.class
// )
public class Storage {
    
    @Id
    @SequenceGenerator(name = "storage_sequence", sequenceName = "storage_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "storage_sequence")
    private Long storageId;

    @NotNull
    private int size;

    // @Type(type = "shape_array")
    // @Column(name = "storageShape",
    //         columnDefinition = "integer[][]")
    // private int[][] storageShape;

    private int storageLeft;

    @OneToMany(mappedBy = "storage",
                orphanRemoval = true,
                cascade = CascadeType.ALL)
    private List<Container> containers;
}
