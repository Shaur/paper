package ru.comics.get.security.service;

import ru.comics.get.security.dto.CredentialsDto;
import ru.comics.get.security.dto.UserTokenDto;
import ru.comics.get.security.dto.user.CreateUserRequestDto;
import ru.comics.get.security.model.User;

public interface UserService {
    User verify(String credentials);

    UserTokenDto authenticate(CredentialsDto credentials);

    void logout();

    User createUser(CreateUserRequestDto requestDto);
}
