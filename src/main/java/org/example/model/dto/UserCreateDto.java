package org.example.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.AuthRole;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private AuthRole role;
}
