package org.example.mapper;

import org.example.model.AuthUser;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserDTO;
import org.example.model.dto.UserUpdateDto;
import org.example.model.enums.AuthRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO toDTO(AuthUser authUser) {
        return UserDTO.builder()
                .id(authUser.getId())
                .fullName(authUser.getFullName())
                .username(authUser.getUsername())
                .phone(authUser.getPhone())
                .role(authUser.getRole())
                .build();
    }

    public List<UserDTO> toDTO(List<AuthUser> authUsers) {
        return  authUsers.stream().map(this::toDTO).toList();
    }


    public AuthUser fromDTO(UserCreateDto dto) {
        return AuthUser.builder()
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public AuthUser fromDTO(AuthUser user, UserUpdateDto dto) {
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        return user;
    }
}
