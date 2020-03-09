package ru.comics.get.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.comics.get.security.dto.user.CreateUserRequestDto;
import ru.comics.get.security.service.UserService;
import ru.comics.get.dto.user.UserResponseDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        return UserResponseDto.of(userService.createUser(requestDto));
    }
}
