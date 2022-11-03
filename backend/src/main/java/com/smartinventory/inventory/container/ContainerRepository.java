package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartinventory.inventory.storage.Storage;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long>{
    //Create, Read, Update, Delete
    List<Container> findByCapacity(Double capacity);
    Optional<Container> findByIAndJ(int i, int j);
    Optional<List<Container>> findByStorage(Storage storage);
}