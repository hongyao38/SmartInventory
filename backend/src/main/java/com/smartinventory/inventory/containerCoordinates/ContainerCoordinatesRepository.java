package com.smartinventory.inventory.containerCoordinates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerCoordinatesRepository extends JpaRepository<ContainerCoordinates, Long> {
    
}
