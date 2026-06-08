package org.example.service;

import org.example.mapper.UserMapper;
import org.example.model.AuthUser;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserDTO;
import org.example.model.dto.UserUpdateDto;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(@Qualifier("userRepositoryDataBase") UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsersList() {
        List<AuthUser> allUsers = userRepository.getAllUsers();
        return userMapper.toDTO(allUsers);
    }

    public UserDTO create(UserCreateDto dto){
        AuthUser authUser = userMapper.fromDTO(dto);
        authUser.setCreatedAt(LocalDateTime.now());

        String encode = passwordEncoder.encode(authUser.getPassword());
        authUser.setPassword(encode);

        userRepository.saveUser(authUser);
        return userMapper.toDTO(authUser);
    }

    public UserDTO getById(Integer id) {
        AuthUser authUser = userRepository.getUserById(id).orElseThrow(
                () -> new RuntimeException("User topilmadi")
        );
        return userMapper.toDTO(authUser);
    }

    public UserDTO update(Integer id, UserUpdateDto dto) {
        AuthUser authUser = userRepository.getUserById(id).orElseThrow(
                () -> new RuntimeException("User topilmadi")
        );
        userMapper.fromDTO(authUser, dto);
        authUser.setUpdatedAt(LocalDateTime.now());
        userRepository.saveUser(authUser);
        return userMapper.toDTO(authUser);
    }

    public void delete(Integer id) {
        AuthUser authUser = userRepository.getUserById(id).orElseThrow(
                () -> new RuntimeException("User topilmadi")
        );
        userRepository.deleteUser(authUser);
    }
}
