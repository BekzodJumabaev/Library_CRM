package org.example.repository.InMemory;

import org.example.model.AuthUser;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserRepositoryDB implements UserRepository {
    List<AuthUser> USERS = new CopyOnWriteArrayList<>();

    @Override
    public List<AuthUser> getAllUsers() {
        return USERS.stream().filter(authUser -> authUser.getDeleted() == null || !authUser.getDeleted()).toList();
    }

    @Override
    public Optional<AuthUser> getUserById(Integer id) {
        return USERS.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public AuthUser saveUser(AuthUser user) {
        Optional<AuthUser> userById = getUserById(user.getId());
        userById.ifPresent(USERS::remove);
        USERS.add(user);
        return user;
    }

    @Override
    public void deleteUser(AuthUser user) {
        user.setDeleted(true);
        USERS.add(user);
    }
}
