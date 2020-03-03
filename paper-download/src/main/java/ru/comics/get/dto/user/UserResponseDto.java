package ru.comics.get.dto.user;

import lombok.Data;
import ru.comics.get.auth.model.User;
import ru.comics.get.auth.model.UserRole;

@Data
public class UserResponseDto {

    private final Long id;

    private final String userName;

    private final UserRole userRole;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                UserRole.USER
        );
    }

}
