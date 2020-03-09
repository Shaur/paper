package ru.comics.get.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
    
    public AuthenticationException(String message) {
        super(message);
    }
    
    public HttpStatus getStatus() {
        return status;
    }
}
