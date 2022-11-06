package com.smartinventory.inventory.block;

import java.util.List;
import java.util.Optional;

import com.smartinventory.inventory.storage.Storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlockRepository extends JpaRepository<Block, Long>{
    //Create, Read, Update, Delete
    Optional<Block> findByIAndJAndStorage(int i, int j, Storage storage);

    List<Block> findByStorage(Storage storage);
}