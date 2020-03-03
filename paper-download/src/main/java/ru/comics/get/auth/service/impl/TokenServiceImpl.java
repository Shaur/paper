package ru.comics.get.auth.service.impl;

import org.springframework.stereotype.Service;
import ru.comics.get.auth.service.TokenService;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class TokenServiceImpl implements TokenService {
    private static final int TOKEN_MAX_LENGTH = 40;

    private final SecureRandom random;

    public TokenServiceImpl() throws NoSuchAlgorithmException {
        random = SecureRandom.getInstanceStrong();
    }

    @Override
    public String generateToken() {
        byte[] array = new byte[TOKEN_MAX_LENGTH];

        random.nextBytes(array);

        return new String(
                Base64.getUrlEncoder().encode(array),
                StandardCharsets.UTF_8
        );
    }
}
