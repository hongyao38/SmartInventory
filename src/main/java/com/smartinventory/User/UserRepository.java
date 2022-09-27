package com.smartinventory.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, String> {
    
    String findEmailByUsername(String username);
    Optional<User> findByUsername(String username);

	Optional<User> findByEmailIgnoreCase(String emailId);
        
    
}
