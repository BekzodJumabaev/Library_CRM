package org.example.service.details;

import org.example.repository.jpa.UserRepositoryJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryJpa userRepositoryJpa;

    public CustomUserDetailsService(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoryJpa.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User topilmadi " + username)
        );
    }
}
