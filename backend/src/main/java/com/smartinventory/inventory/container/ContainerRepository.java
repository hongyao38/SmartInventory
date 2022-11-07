package com.smartinventory.inventory.container;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartinventory.inventory.food.Food;
import com.smartinventory.inventory.storage.Storage;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long>{
    //Create, Read, Update, Delete
    List<Container> findByCapacity(Double capacity);
    Optional<Container> findByIAndJAndStorage(int i, int j, Storage storage);
    Optional<List<Container>> findByStorage(Storage storage);
    Optional<Container> findByStorageAndFood(Storage storage, Food Food);
    
    @Transactional
    @Modifying
    @Query("UPDATE Container c SET c.food = ?1, c.quantity = ?2 WHERE c.id = ?3")
    int updateContainerWithFood(Food food, Double quantity, Long containerId);

    @Transactional
    @Modifying
    @Query("UPDATE Container c SET c.capacity = ?2 WHERE c.id = ?1")
    int updateContainerCapacity(Long containerId, Double capacity);
}