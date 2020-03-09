package ru.comics.get.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.comics.get.security.configuration.BearerToken;
import ru.comics.get.security.service.UserService;

import java.util.List;

@Component
public class BearerTokenAuthProvider implements AuthenticationProvider {

    private final UserService userService;

    public BearerTokenAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var bearerToken = (BearerToken) authentication;
        var user = userService.verify(bearerToken.getCredentials());

        return new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                null,
                List.of(new SimpleGrantedAuthority(user.getUserRole().name()))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerToken.class.isAssignableFrom(authentication);
    }
}
