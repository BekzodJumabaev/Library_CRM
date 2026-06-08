package org.example.repository;

import org.example.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository /*extends JpaRepository<AuthUser, Integer>*/ {
    List<AuthUser> getAllUsers();
    Optional<AuthUser> getUserById(Integer id);
    AuthUser saveUser(AuthUser user);
    void deleteUser(AuthUser user);
}
