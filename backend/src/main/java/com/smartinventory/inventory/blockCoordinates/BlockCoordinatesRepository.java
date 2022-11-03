package com.smartinventory.inventory.blockCoordinates;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartinventory.inventory.storage.Storage;

@Repository
public interface BlockCoordinatesRepository extends JpaRepository<BlockCoordinates, Long> {
    Optional<List<BlockCoordinates>> findByStorage(Storage storage);
}
