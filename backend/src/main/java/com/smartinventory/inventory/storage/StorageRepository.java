package com.smartinventory.inventory.storage;

import java.util.List;
import java.util.Optional;

import com.smartinventory.appuser.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long>{
    //Create, Read, Update, Delete
    Optional<Storage> findByUsername(String username);
}
