package com.smartinventory.inventory.block;

import java.util.List;
import java.util.Optional;

import com.smartinventory.exceptions.inventory.BlockExistsException;
import com.smartinventory.exceptions.inventory.BlockNotFoundException;
import com.smartinventory.inventory.storage.Storage;
import com.smartinventory.inventory.storage.StorageRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlockService {

    private final BlockRepository blockRepo;
    private final StorageRepository storageRepo;

    /**
     * Add new block in storage
     * @param i
     * @param j
     * @return Block
     */
    public Block addBlock(String username, Integer i, Integer j) {
        Optional<Storage> storage = storageRepo.findByUsername(username);

        // if (storage.isEmpty()) {
        //     throw new StorageNotFoundException();
        // }

        Optional<Block> block = blockRepo.findByIAndJ(i, j);

        //check if block is already present
        if (block.isPresent()) {
            throw new BlockExistsException();
        }

        // Creating new block
        Block newBlock = new Block(i, j, storage.get());

        return blockRepo.save(newBlock);
    }


    /**
     * Takes in username and return a list of blocks used for rendering
     * storage space upon logging in
     * @param username
     * @return List<Block> blocks
     */
    public List<Block> getAllBlocksFromUser(String username) {
        Optional<Storage> storage = storageRepo.findByUsername(username);

        // if (storage.isEmpty()) {
        //     throw new StorageNotFoundException();
        // }

        // Get a list of all blocks belonging to user
        List<Block> blocks = blockRepo.findByStorage(storage.get());
        // System.out.println(blocks);
        return blocks;
    }

    public Block getBlock(String username, Integer i, Integer j) {
        Optional<Storage> storage = storageRepo.findByUsername(username);

        // if (storage.isEmpty()) {
        //     throw new StorageNotFoundException();
        // }

        // Get block
        Optional<Block> block = blockRepo.findByIAndJ(i, j);

        if (block.isEmpty()) {
            throw new BlockNotFoundException();
        }

        // System.out.println(blocks);
        return block.get();
    }
}
