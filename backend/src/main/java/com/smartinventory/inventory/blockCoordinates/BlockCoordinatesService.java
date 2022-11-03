package com.smartinventory.inventory.containerCoordinates;

import com.smartinventory.inventory.blockCoordinates.ContainerCoordinatesRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlockCoordinatesService {
    
    private final BlockCoordinatesRepository BlockServiceRepo;

}
