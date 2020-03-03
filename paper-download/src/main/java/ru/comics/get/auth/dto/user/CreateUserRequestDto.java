package ru.comics.get.auth.dto.user;

import lombok.Data;
import ru.comics.get.auth.model.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequestDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
    
    @NotNull
    private UserRole role;
}
