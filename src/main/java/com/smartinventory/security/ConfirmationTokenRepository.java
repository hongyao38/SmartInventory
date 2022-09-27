package com.smartinventory.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
}
