package ru.comics.get.security.dto;

import lombok.Builder;
import lombok.Data;
import ru.comics.get.security.model.Token;

@Data
@Builder
public class UserTokenDto {
    private final String name;
    private final TokenDto token;

    public static UserTokenDto of(Token token) {
        return UserTokenDto.builder()
                .name(token.getUser().getUserName())
                .token(TokenDto.of(token))
                .build();
    }
}
