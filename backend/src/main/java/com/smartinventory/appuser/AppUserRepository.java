package com.smartinventory.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    String findEmailByUsername(String username);

    Optional<AppUser> findById(Long id);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser u SET u.isEnabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser u SET u.password = ?2 WHERE u.email = ?1")
    int resetPassword(String email, String password);
}
