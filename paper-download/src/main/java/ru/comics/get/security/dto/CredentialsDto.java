package ru.comics.get.security.dto;

import lombok.Data;

@Data
public class CredentialsDto {
    private final String userName;
    private final String password;
}
