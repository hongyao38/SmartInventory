package com.smartinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventory.exceptions.inventory.block.BlockExistsException;
import com.smartinventory.exceptions.inventory.block.BlockNotFoundException;
import com.smartinventory.exceptions.inventory.storage.StorageNotFoundException;
import com.smartinventory.inventory.block.Block;
import com.smartinventory.inventory.block.BlockDTO;
import com.smartinventory.inventory.block.BlockRepository;
import com.smartinventory.inventory.block.BlockService;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {
    @Mock
    private BlockRepository blockRepo;

    @Mock
    private StorageRepository storageRepo;

    @InjectMocks
    private BlockService blockService;

    @Test
    void addBlock_StoragePresentBlockPresent_ReturnBlockExistsException() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        Block block = new Block(1, 1, optionalStorage.get());
        Optional<Block> optionalBlock = Optional.of(block);
        BlockDTO blockRequest = new BlockDTO();
        blockRequest.setI(1);
        blockRequest.setJ(1);

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByIAndJAndStorage(1, 1, optionalStorage.get())).thenReturn(optionalBlock);

        // Block returnedBlock = blockService.addBlock("TestUser", blockRequest);
        // assertEquals(returnedBlock, block);

        assertThrows(BlockExistsException.class, () -> blockService.addBlock("TestUser", blockRequest));

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByIAndJAndStorage(1, 1, optionalStorage.get());
    }

    @Test
    void addBlock_StorageNotFound_ReturnStorageNotFoundException() {
        Optional<Storage> optionalStorage = Optional.empty();

        BlockDTO blockRequest = new BlockDTO();
        blockRequest.setI(1);
        blockRequest.setJ(1);

        when(storageRepo.findByUsername(("TestUser"))).thenReturn(optionalStorage);

        assertThrows(StorageNotFoundException.class, () -> blockService.addBlock("TestUser", blockRequest));

        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void addBlock_StoragePresentBlockNotPresent_ReturnBlock() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        Block block = new Block(1, 1, optionalStorage.get());
        Optional<Block> optionalBlock = Optional.empty();
        BlockDTO blockRequest = new BlockDTO();
        blockRequest.setI(1);
        blockRequest.setJ(1);

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByIAndJAndStorage(1, 1, optionalStorage.get())).thenReturn(optionalBlock);
        when(blockRepo.save(any(Block.class))).thenReturn(block);

        Block returnedBlock = blockService.addBlock("TestUser", blockRequest);
        assertEquals(returnedBlock, block);

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByIAndJAndStorage(1, 1, optionalStorage.get());
    }

    @Test
    void getAllBlocksFromUser_StoragePresentListPresent_ReturnList() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        Block block1 = new Block(1, 1, optionalStorage.get());
        Block block2 = new Block(2, 2, optionalStorage.get());
        List<Block> blockList = new ArrayList<>();
        blockList.add(block1);
        blockList.add(block2);

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByStorage(storage)).thenReturn(blockList);

        List<Block> returnedList = blockService.getAllBlocksFromUser("TestUser");
        assertEquals(returnedList, blockList);

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByStorage(storage);
    }

    @Test
    void getAllBlocksFromUser_StorageNotFound_ReturnStorageNotFoundException() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.empty();

        Block block1 = new Block(1, 1, storage);
        Block block2 = new Block(2, 2, storage);
        List<Block> blockList = new ArrayList<>();
        blockList.add(block1);
        blockList.add(block2);

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        assertThrows(StorageNotFoundException.class, () -> blockService.getAllBlocksFromUser("TestUser"));

        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getAllBlocksFromUser_StorageFoundListEmpty_ReturnEmptyList() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        List<Block> blockList = new ArrayList<>();

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByStorage(storage)).thenReturn(blockList);

        List<Block> returnedList = blockService.getAllBlocksFromUser("TestUser");
        assertEquals(returnedList, blockList);

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByStorage(storage);
    }

    @Test
    void getBlock_StorageFoundBlockNotFound_ReturnBlockNotFoundException() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        Optional<Block> optionalBlock = Optional.empty();

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByIAndJAndStorage(1, 1, storage)).thenReturn(optionalBlock);

        // List<Block> returnedList = blockService.getAllBlocksFromUser("TestUser");
        // assertEquals(returnedList, blockList);

        assertThrows(BlockNotFoundException.class, () -> blockService.getBlock("TestUser", 1, 1));

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByIAndJAndStorage(1, 1, storage);
    }

    @Test
    void getBlock_StorageNotFound_ReturnStorageNotFoundException() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.empty();

        Optional<Block> optionalBlock = Optional.empty();

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);

        // List<Block> returnedList = blockService.getAllBlocksFromUser("TestUser");
        // assertEquals(returnedList, blockList);

        assertThrows(StorageNotFoundException.class, () -> blockService.getBlock("TestUser", 1, 1));

        verify(storageRepo).findByUsername("TestUser");
    }

    @Test
    void getBlock_StorageFoundBlockFound_ReturnBlock() {
        Storage storage = new Storage();
        storage.setUsername("TestUser");
        Optional<Storage> optionalStorage = Optional.of(storage);

        Block block = new Block(1, 1, optionalStorage.get());
        Optional<Block> optionalBlock = Optional.of(block);

        when(storageRepo.findByUsername("TestUser")).thenReturn(optionalStorage);
        when(blockRepo.findByIAndJAndStorage(1, 1, storage)).thenReturn(optionalBlock);

        Block returnedBlock = blockService.getBlock("TestUser",1,1);
        assertEquals(returnedBlock, block);

        verify(storageRepo).findByUsername("TestUser");
        verify(blockRepo).findByIAndJAndStorage(1, 1, storage);
    }

}
