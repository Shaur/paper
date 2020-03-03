package ru.comics.get.auth.service;

import ru.comics.get.auth.dto.CredentialsDto;
import ru.comics.get.auth.dto.UserTokenDto;
import ru.comics.get.auth.dto.user.CreateUserRequestDto;
import ru.comics.get.auth.model.User;

public interface UserService {
    User verify(String credentials);

    UserTokenDto authenticate(CredentialsDto credentials);

    void logout();

    User createUser(CreateUserRequestDto requestDto);
}
