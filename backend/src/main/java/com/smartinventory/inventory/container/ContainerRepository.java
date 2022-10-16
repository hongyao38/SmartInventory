package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long>{
    //Create, Read, Update, Delete
    List<Container> findByCapacity(Double capacity);
}
