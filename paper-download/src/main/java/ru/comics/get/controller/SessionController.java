package ru.comics.get.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.comics.get.auth.dto.CredentialsDto;
import ru.comics.get.auth.dto.UserTokenDto;
import ru.comics.get.auth.exception.AuthenticationException;
import ru.comics.get.auth.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sessions")
    public UserTokenDto issueToken(@RequestBody CredentialsDto credentials) {
        return userService.authenticate(credentials);
    }

    @DeleteMapping("/sessions")
    public void logout() {
        userService.logout();
    }
}
