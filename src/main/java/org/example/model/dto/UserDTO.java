package org.example.model.dto;

import lombok.*;
import org.example.model.enums.AuthRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String fullName;
    private String username;
    private String phone;
    private AuthRole role;
}
