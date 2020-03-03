package ru.comics.get.auth.dto;

import lombok.Data;

@Data
public class CredentialsDto {
    private final String userName;
    private final String password;
}
