package com.smartinventory;

import com.smartinventory.inventory.block.BlockRepository;
import com.smartinventory.inventory.block.BlockService;
import com.smartinventory.inventory.storage.StorageRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {
    @Mock
    private BlockRepository blockRepo;

    @Mock
    private StorageRepository storageRepo;

    @InjectMocks
    private BlockService blockService;
    
    
}
