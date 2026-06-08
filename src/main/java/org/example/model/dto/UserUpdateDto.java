package org.example.model.dto;

import lombok.*;
import org.example.model.enums.AuthRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String fullName;
    private String username;
    private String phone;
    private AuthRole role;
}
