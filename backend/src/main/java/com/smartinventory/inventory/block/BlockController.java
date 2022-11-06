package com.smartinventory.inventory.block;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class BlockController {

    private final BlockService blockService;
    
    // Add new block
    @PostMapping("/blocks/{username}")
    public Block addBlock(@PathVariable("username") String username, @RequestBody BlockDTO blockRequest) {
        System.out.println(blockRequest);
        return blockService.addBlock(username, blockRequest);
    }

    // Get all blocks
    @GetMapping("/blocks/{username}")
    public List<Block> getAllBlocksFromUser(@PathVariable("username") String username) {
        return blockService.getAllBlocksFromUser(username);
    }

    // Get a specific block
    @GetMapping("blocks/{username}/{i}_{j}")
    public Block getBlock(@PathVariable("username") String username,
                            @PathVariable("i") Integer i,
                            @PathVariable("j") Integer j) {
        return blockService.getBlock(username, i, j);
    }
}
