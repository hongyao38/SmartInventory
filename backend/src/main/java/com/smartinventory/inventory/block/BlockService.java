package com.smartinventory.inventory.block;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartinventory.exceptions.inventory.BlockExistsException;
import com.smartinventory.exceptions.inventory.BlockNotFoundException;
import com.smartinventory.exceptions.inventory.StorageNotFoundException;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlockService {

    private final BlockRepository blockRepo;
    private final StorageRepository storageRepo;


    private Storage getStorageByUsername(String username) {
        Optional<Storage> storage = storageRepo.findByUsername(username);
        if (storage.isEmpty()) {
            throw new StorageNotFoundException(username);
        }
        return storage.get();
    }

    /**
     * Add new block in storage
     * @param i
     * @param j
     * @return Block
     */
    public Block addBlock(String username, BlockDTO blockRequest) {
        
        Storage storage = getStorageByUsername(username);

        int i = blockRequest.getI();
        int j = blockRequest.getJ();
        Optional<Block> block = blockRepo.findByIAndJAndStorage(i, j, storage);
        //check if block is already present
        if (block.isPresent()) {
            throw new BlockExistsException();
        }
        // Creating new block
        Block newBlock = new Block(i, j, storage);
        return blockRepo.save(newBlock);
    }


    /**
     * Takes in username and return a list of blocks used for rendering
     * storage space upon logging in
     * @param username
     * @return List<Block> blocks
     */
    public List<Block> getAllBlocksFromUser(String username) {
        Storage storage = getStorageByUsername(username);

        // Get a list of all blocks belonging to user
        List<Block> blocks = blockRepo.findByStorage(storage);
        // System.out.println(blocks);
        return blocks;
    }

    /**
     * Retrieve an individual block belonging to username
     * @param username
     * @param i
     * @param j
     * @return Block
     */
    public Block getBlock(String username, Integer i, Integer j) {
        Storage storage = getStorageByUsername(username);

        // Get block
        Optional<Block> block = blockRepo.findByIAndJAndStorage(i, j, storage);
        if (block.isEmpty()) {
            throw new BlockNotFoundException();
        }
        // System.out.println(blocks);
        return block.get();
    }
}
