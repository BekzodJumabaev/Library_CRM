package org.example.repository.jpa;

import org.example.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<AuthUser, Integer> {

    Optional<AuthUser> findByUsername(String username);
}
