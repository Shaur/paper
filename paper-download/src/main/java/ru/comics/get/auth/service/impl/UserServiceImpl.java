package ru.comics.get.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.comics.get.auth.dto.CredentialsDto;
import ru.comics.get.auth.dto.UserTokenDto;
import ru.comics.get.auth.dto.user.CreateUserRequestDto;
import ru.comics.get.auth.exception.AuthenticationException;
import ru.comics.get.auth.exception.UserAlreadyExistsException;
import ru.comics.get.auth.model.Token;
import ru.comics.get.auth.model.User;
import ru.comics.get.auth.repository.TokenRepository;
import ru.comics.get.auth.repository.UserRepository;
import ru.comics.get.auth.service.TokenService;
import ru.comics.get.auth.service.UserService;
import ru.comics.get.exception.ServiceErrorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    private final MessageDigest messageDigest;

    private final long tokenLifetime;

    public UserServiceImpl(
            TokenRepository tokenRepository,
            UserRepository userRepository,
            TokenService tokenService,
            @Value("${token-lifetime}") Long tokenLifetime
    ) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;

        this.tokenLifetime = tokenLifetime;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceErrorException("Error initialization, no SHA-256 algorithm available", e);
        }
    }

    @Override
    public User verify(String tokenValue) {
        Token token = tokenRepository.findById(tokenValue)
                .orElseThrow(() -> new AuthenticationException("Invalid token value"));

        if (System.currentTimeMillis() > token.getExpirationTime()) {
            throw new AuthenticationException("Token is expired");
        }

        return token.getUser();
    }

    @Override
    public UserTokenDto authenticate(CredentialsDto credentials) {
        var user = userRepository.findFirstByUserName(credentials.getUserName())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        var isVerified = verifyPassword(credentials, user);
        if(!isVerified) {
            throw new AuthenticationException("Authentication failed. Wrong username or password");
        }

        var token = new Token(
                tokenService.generateToken(),
                user,
                System.currentTimeMillis() + tokenLifetime
        );

        return UserTokenDto.of(tokenRepository.save(token));
    }

    private boolean verifyPassword(CredentialsDto credentials, User user) {
        var value = credentials.getPassword() + user.getSalt();
        messageDigest.update(value.getBytes(StandardCharsets.UTF_8));
        var secretHash = Base64.getEncoder().encodeToString(messageDigest.digest());

        if(!secretHash.equals(user.getSecretHash())) {
            log.error("Authentication failed for {}, invalid password", user.getUserName());
            return false;
        }

        return true;
    }

    @Override
    public void logout() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            log.info("Logout for user {} requested", authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    @Override
    public User createUser(CreateUserRequestDto requestDto) {
        userRepository.findFirstByUserName(requestDto.getUserName())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(requestDto.getUserName());
                });

        var saltUuid = UUID.randomUUID();
        String salt = Long.toHexString(saltUuid.getLeastSignificantBits()) + Long.toHexString(saltUuid.getMostSignificantBits());

        String hash = Base64.getEncoder().encodeToString(
                messageDigest.digest((requestDto.getPassword() + salt).getBytes(StandardCharsets.UTF_8))
        );

        var user = User.builder()
                .userName(requestDto.getUserName())
                .salt(salt)
                .secretHash(hash)
                .userRole(requestDto.getRole())
                .build();

        return userRepository.save(user);
    }
}
