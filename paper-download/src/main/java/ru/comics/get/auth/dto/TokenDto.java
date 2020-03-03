package ru.comics.get.auth.dto;

import lombok.Builder;
import lombok.Data;
import ru.comics.get.auth.model.Token;

@Data
@Builder
public class TokenDto {
    private final String value;
    private final Long expirationTime;

    public static TokenDto of(Token token) {
        return TokenDto.builder()
                .value(token.getValue())
                .expirationTime(token.getExpirationTime())
                .build();
    }
}
