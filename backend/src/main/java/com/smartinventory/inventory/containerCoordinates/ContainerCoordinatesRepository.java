package com.smartinventory.inventory.containerCoordinates;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartinventory.inventory.storage.Storage;

@Repository
public interface ContainerCoordinatesRepository extends JpaRepository<ContainerCoordinates, Long> {
    Optional<List<ContainerCoordinates>> findByStorage(Storage storage);
}
